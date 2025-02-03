package com.qriz.app.feature.sign.findPassword.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qriz.app.core.designsystem.component.NavigationType
import com.qriz.app.core.designsystem.component.QrizTextFiled
import com.qriz.app.core.designsystem.component.QrizTopBar
import com.qriz.app.core.designsystem.component.SupportingText
import com.qriz.app.core.designsystem.theme.Black
import com.qriz.app.core.designsystem.theme.Gray100
import com.qriz.app.core.designsystem.theme.Gray200
import com.qriz.app.core.designsystem.theme.Gray300
import com.qriz.app.core.designsystem.theme.Gray800
import com.qriz.app.core.designsystem.theme.Mint800
import com.qriz.app.core.designsystem.theme.QrizTheme
import com.qriz.app.feature.base.extention.collectSideEffect
import com.qriz.app.feature.sign.R
import com.qriz.app.feature.sign.signup.component.SignUpBasePage
import com.quiz.app.core.data.user.user_api.model.ID_MAX_LENGTH

@Composable
fun FindPasswordAuthScreen(
    viewModel: FindPasswordAuthViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateReset: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        when (it) {
            else -> {}
        }
    }

    FindPasswordAuthContent(
        email = uiState.email,
        authNumber = uiState.authNumber,
        authTimerText = uiState.authTimerText,
        showAuthNumberLayout = uiState.showAuthNumberLayout,
        enableInputAuthNumber = uiState.enableInputAuthNumber,
        verifiedAuthNumber = uiState.verifiedAuthNumber,
        emailSupportingTextResId = uiState.emailSupportingTextResId,
        emailSupportingTextColor = uiState.emailSupportingTextColor,
        authNumberSupportingTextResId = uiState.authNumberSupportingTextResId,
        authNumberSupportingTextColor = uiState.authNumberSupportingTextColor,
        authNumberRequiredBorderColor = uiState.authNumberRequiredBorderColor,
        emailButtonTextColor = uiState.emailButtonTextColor,
        onEmailChanged = {
            viewModel.process(FindPasswordAuthUiAction.OnChangeEmail(email = it))
        },
        onAuthNumberChanged = {
            viewModel.process(FindPasswordAuthUiAction.OnChangeAuthNumber(authNumber = it))
        },
        onSendAuthNumberEmail = {
            viewModel.process(FindPasswordAuthUiAction.SendAuthNumberEmail)
        },
        onBack = onBack,
        onNavigateReset = onNavigateReset,
    )

}

@Composable
private fun FindPasswordAuthContent(
    email: String,
    authNumber: String,
    authTimerText: String,
    showAuthNumberLayout: Boolean,
    verifiedAuthNumber: Boolean,
    enableInputAuthNumber: Boolean,
    emailSupportingTextResId: Int,
    emailSupportingTextColor: Color,
    authNumberSupportingTextResId: Int,
    authNumberSupportingTextColor: Color,
    authNumberRequiredBorderColor: Boolean,
    emailButtonTextColor: Color,
    onBack: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onSendAuthNumberEmail: () -> Unit,
    onAuthNumberChanged: (String) -> Unit,
    onNavigateReset: () -> Unit,
) {

    Column {
        QrizTopBar(
            title = stringResource(R.string.find_password),
            navigationType = NavigationType.BACK,
            onNavigationClick = onBack,
        )

        SignUpBasePage(
            title = stringResource(R.string.find_password_auth_title),
            subTitle = stringResource(R.string.find_password_auth_guide),
            buttonText = stringResource(R.string.reset_password),
            buttonEnabled = verifiedAuthNumber,
            onButtonClick = onNavigateReset,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                QrizTextFiled(
                    value = email,
                    onValueChange = onEmailChanged,
                    supportingText = if (emailSupportingTextResId != R.string.empty) {
                        SupportingText(
                            message = stringResource(emailSupportingTextResId),
                            color = emailSupportingTextColor,
                            isBorderColorRequired = false,
                        )
                    } else {
                        null
                    },
                    singleLine = true,
                    hint = stringResource(R.string.email_sample_hint),
                    maxLength = ID_MAX_LENGTH,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 14.dp,
                    )
                )
                OutlinedButton(
                    onClick = onSendAuthNumberEmail,
                    border = BorderStroke(
                        width = 1.dp,
                        color = Gray200,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(
                        horizontal = 27.5.dp,
                        vertical = 14.dp
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        stringResource(
                            id = if (showAuthNumberLayout) R.string.resend else R.string.send
                        ),
                        color = emailButtonTextColor,
                        style = QrizTheme.typography.subhead,
                    )
                }
            }

            if (showAuthNumberLayout) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    QrizTextFiled(
                        value = authNumber,
                        onValueChange = onAuthNumberChanged,
                        supportingText = if (authNumberSupportingTextResId != R.string.empty) {
                            SupportingText(
                                message = stringResource(authNumberSupportingTextResId),
                                color = authNumberSupportingTextColor,
                                isBorderColorRequired = authNumberRequiredBorderColor,
                            )
                        } else {
                            null
                        },
                        singleLine = true,
                        hint = stringResource(R.string.sign_up_auth_page_hint),
                        maxLength = ID_MAX_LENGTH,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 14.dp,
                        )
                    )
                    OutlinedButton(
                        onClick = onSendAuthNumberEmail,
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(
                            horizontal = 27.5.dp,
                            vertical = 14.dp
                        ),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            stringResource(R.string.verify),
                            color = MaterialTheme.colorScheme.primary,
                            style = QrizTheme.typography.subhead,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FindPasswordAuthContentPreview() {
    QrizTheme {
        FindPasswordAuthContent(
            email = "",
            authNumber = "",
            authTimerText = "",
            showAuthNumberLayout = true,
            verifiedAuthNumber = true,
            enableInputAuthNumber = true,
            emailSupportingTextResId = R.string.empty,
            emailSupportingTextColor = Mint800,
            authNumberSupportingTextResId = R.string.empty,
            authNumberSupportingTextColor = Mint800,
            authNumberRequiredBorderColor = false,
            emailButtonTextColor = Gray800,
            onEmailChanged = {},
            onAuthNumberChanged = {},
            onSendAuthNumberEmail = {},
            onBack = {},
            onNavigateReset = {},
        )
    }
}
