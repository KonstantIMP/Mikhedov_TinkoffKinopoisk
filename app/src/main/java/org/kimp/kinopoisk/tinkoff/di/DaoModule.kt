package org.kimp.kinopoisk.tinkoff.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.kimp.kinopoisk.tinkoff.data.base.FilmsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideFilmsDao(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FilmsDatabase::class.java,
        "films"
    ).build().filmsDao()
}