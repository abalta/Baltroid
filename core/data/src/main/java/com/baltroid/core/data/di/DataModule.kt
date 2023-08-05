package com.baltroid.core.data.di

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.core.data.repository.CitiesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsCitiesRepository(
        citiesRepositoryImpl: CitiesRepositoryImpl
    ): CitiesRepository
}