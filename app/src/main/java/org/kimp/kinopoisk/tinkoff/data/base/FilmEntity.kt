package org.kimp.kinopoisk.tinkoff.data.base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val filmId: Int,
    val name: String,
    val poster: String,
    val preview: String,
    val year: Int,
    val description: String,
    val genres: List<String> = listOf(),
    val countries: List<String> = listOf()
)
