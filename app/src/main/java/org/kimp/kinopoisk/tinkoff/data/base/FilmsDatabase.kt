package org.kimp.kinopoisk.tinkoff.data.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.kimp.kinopoisk.tinkoff.util.Converter

@Database(entities = [FilmEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class FilmsDatabase: RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}