package com.baltroid.di

import com.baltroid.core.datastore.PreferencesDataStoreDataSource
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
    fun provideBaltroidApi(dataSource: PreferencesDataStoreDataSource) = BaltroidApi(dataSource)

    @Provides
    fun provideHitReadsService(api: BaltroidApi) = api.hitReadsService
}
