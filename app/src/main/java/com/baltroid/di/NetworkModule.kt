package com.baltroid.di

import android.content.Context
import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.api.BaltroidApi
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
    fun provideBaltroidApi(
        dataSource: PreferencesDataStoreDataSource,
        @ApplicationContext context: Context
    ) = BaltroidApi(dataSource, context)

    @Provides
    fun provideHitReadsService(api: BaltroidApi) = api.hitReadsService
}
