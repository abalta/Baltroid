package com.baltroid.apps.di

import com.baltroid.core.data.repository.MekikRepositoryImpl
import com.mobven.domain.repository.MekikRepository
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
    fun bindMekikRepository(mekikRepositoryImple: MekikRepositoryImpl): MekikRepository

}
