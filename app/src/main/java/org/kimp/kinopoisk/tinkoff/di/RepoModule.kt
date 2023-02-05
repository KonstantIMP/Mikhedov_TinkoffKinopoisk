package org.kimp.kinopoisk.tinkoff.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kimp.kinopoisk.tinkoff.data.repo.FavoritesRepository
import org.kimp.kinopoisk.tinkoff.data.repo.FavoritesRepositoryImpl
import org.kimp.kinopoisk.tinkoff.data.repo.FilmsRepository
import org.kimp.kinopoisk.tinkoff.data.repo.FilmsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Singleton
    @Binds
    abstract fun FilmsRepositoryImpl.provideFilmsRepository(): FilmsRepository

    @Singleton
    @Binds
    abstract fun FavoritesRepositoryImpl.provideFavoritesRepository(): FavoritesRepository
}
