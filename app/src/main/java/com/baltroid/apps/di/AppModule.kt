package com.baltroid.apps.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.rosariopfernandes.firecoil.StorageReferenceFetcher
import io.github.rosariopfernandes.firecoil.StorageReferenceKeyer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext application: Context): ImageLoader =
        ImageLoader.Builder(application).components {
            add(StorageReferenceKeyer())
            add(StorageReferenceFetcher.Factory())
        }.memoryCache {
            MemoryCache.Builder(application)
                .build()
        }.diskCache {
            DiskCache.Builder()
                .directory(application.cacheDir.resolve("image_cache"))
                .build()
        }.build()
}