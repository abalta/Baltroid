package com.baltroid.apps.di

import android.content.Context
import com.baltroid.core.common.EventBus
import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.network.api.MekikApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMekikApi(tokenProvider: PreferencesHelper) = MekikApi(tokenProvider)


    @Provides
    fun provideMekikService(api: MekikApi) = api.mekikService

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context) = PreferencesHelper(context)

    @Provides
    @Singleton
    fun provideEventBus() = EventBus()

}
