package org.kimp.kinopoisk.tinkoff.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ImageModule {
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        memoryCache: MemoryCache,
        diskCache: DiskCache
    ) = ImageLoader.Builder(context)
        .memoryCache(memoryCache)
        .diskCache(diskCache)
        .crossfade(true)
        .build()


    @Provides
    fun provideMemoryCache(
        @ApplicationContext context: Context
    ) = MemoryCache.Builder(context)
        .maxSizePercent(0.25)
        .build()


    @Provides
    fun provideDiskCache(
        @ApplicationContext context: Context
    ) = DiskCache.Builder()
        .directory(context.cacheDir.resolve("images"))
        .maxSizePercent(0.05)
        .build()
}
