package com.baltroid.di

import com.baltroid.core.network.api.BaltroidApi
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
    fun provideBaltroidApi() = BaltroidApi()

    @Provides
    fun provideHitReadsService(api: BaltroidApi) = api.hitReadsService
}
