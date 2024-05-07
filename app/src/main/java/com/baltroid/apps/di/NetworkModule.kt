package com.baltroid.apps.di

import com.baltroid.core.network.api.MekikApi
import com.baltroid.core.network.api.MekikTokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMekikApi(tokenProvider: MekikTokenProvider) = MekikApi(tokenProvider)

    @Provides
    fun provideMekikTokenProvider() = object : MekikTokenProvider {
        override val token: String? = null
    }

    @Provides
    fun provideMekikService(api: MekikApi) = api.mekikService

}
