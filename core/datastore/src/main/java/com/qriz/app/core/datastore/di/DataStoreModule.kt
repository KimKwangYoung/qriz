package com.qriz.app.core.datastore.di

import android.content.Context
import com.qriz.app.datastore.TokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun providesTokenDataStore(
        @ApplicationContext context: Context
    ): TokenDataStore = TokenDataStore(context)
}
