package com.qriz.app.feature.sign.findPassword.auth

import androidx.lifecycle.viewModelScope
import com.qriz.app.feature.base.BaseViewModel
import com.qriz.app.feature.sign.R
import com.quiz.app.core.data.user.user_api.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordAuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<FindPasswordAuthUiState, FindPasswordAuthUiEffect, FindPasswordAuthUiAction>(
    FindPasswordAuthUiState.DEFAULT
) {
    private var timerJob: Job? = null

    override fun process(action: FindPasswordAuthUiAction): Job = viewModelScope.launch {
        when (action) {
            is FindPasswordAuthUiAction.OnChangeEmail -> {
                updateState { copy(email = action.email) }
            }

            is FindPasswordAuthUiAction.SendAuthNumberEmail -> {
                sendEmail()
            }

            is FindPasswordAuthUiAction.OnChangeAuthNumber -> {
                updateState { copy(authNumber = action.authNumber) }
            }

            is FindPasswordAuthUiAction.VerifyAuthNumber -> {
                verifyAuthNumber()
            }
        }
    }

    private suspend fun sendEmail() {
        val email = uiState.value.email
        val isEmailEmpty = email.isEmpty()
        val isValidEmailFormat = uiState.value.isValidEmailFormat

        if (isEmailEmpty) {
            updateState { copy(emailSupportingTextResId = R.string.email_is_empty) }
            return
        }

        if (isValidEmailFormat.not()) {
            updateState { copy(emailSupportingTextResId = R.string.email_is_invalid_format) }
            return
        }

        runCatching {
            userRepository.sendEmailToFindPassword(email = email)
        }.onSuccess {
            updateState {
                copy(
                    authNumberSupportingTextResId = R.string.success_send_email_auth_number,
                    showAuthNumberLayout = true,
                )
            }
            startTimer()
        }.onFailure {
            updateState { copy(showFailSendEmailDialog = true) }
        }
    }

    private suspend fun verifyAuthNumber() {
        val authNumber = uiState.value.authNumber

        if (uiState.value.isValidAuthNumberFormat.not()) {
            updateState { copy(authNumberSupportingTextResId = R.string.invalid_auth_number_format) }
            return
        }

        runCatching {
            userRepository.verifyEmailAuthNumber(authenticationNumber = authNumber)
        }.onSuccess {
            cancelTimer()
            updateState { copy(authNumberSupportingTextResId = R.string.success_verify_auth_number) }
        }.onFailure {
            //TODO: 상황에 따라서 Dialog or FailMessage
        }
    }

    private fun startTimer() {
        updateState { copy(authTimerMs = AUTH_TIME_LIMIT_MILS) }

        timerJob = viewModelScope.launch {
            val interval = 1000L
            var currentTime = uiState.value.authTimerMs
            while (isActive) {
                if (currentTime <= 0) break

                delay(interval)
                currentTime -= interval
                updateState { copy(authTimerMs = currentTime) }
            }

            updateState { copy(enableInputAuthNumber = false) }
        }
    }

    private fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
        updateState { copy(authTimerMs = 0) }
    }

    companion object {
        const val AUTH_TIME_LIMIT_MILS = 18000L
    }
}
