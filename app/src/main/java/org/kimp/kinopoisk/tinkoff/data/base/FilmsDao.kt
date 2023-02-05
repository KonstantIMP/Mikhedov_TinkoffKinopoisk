package org.kimp.kinopoisk.tinkoff.data.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmsDao {
    @Query("SELECT * FROM films")
    fun getAll(): List<FilmEntity>

    @Insert
    fun insertAll(vararg film: FilmEntity)

    @Query("DELETE FROM films WHERE filmId = :id")
    fun deleteById(id: Int)
}