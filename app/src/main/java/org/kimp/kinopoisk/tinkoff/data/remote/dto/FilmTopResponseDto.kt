package org.kimp.kinopoisk.tinkoff.data.remote.dto

data class FilmTopResponseDto (
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int?,
    val genres: List<GenreDto> = listOf()
)