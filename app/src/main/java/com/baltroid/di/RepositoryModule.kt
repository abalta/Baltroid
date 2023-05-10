package com.baltroid.di

import com.baltroid.core.data.repository.OriginalRepositoryImpl
import com.hitreads.core.domain.repository.OriginalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindOriginalRepository(originalRepositoryImpl: OriginalRepositoryImpl): OriginalRepository

}