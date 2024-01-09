package com.baltroid.apps.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.baltroid.apps.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext application: Context): ImageLoader =
        ImageLoader.Builder(application)
            .memoryCache {
                MemoryCache.Builder(application)
                    .maxSizePercent(0.25)
                    .build()
            }.diskCache {
                DiskCache.Builder()
                    .directory(application.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }.build()
}