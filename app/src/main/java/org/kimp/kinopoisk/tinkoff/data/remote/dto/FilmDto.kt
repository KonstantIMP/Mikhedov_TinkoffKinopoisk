package org.kimp.kinopoisk.tinkoff.data.remote.dto

data class FilmDto(
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int?,
    val description: String?,
    val genres: List<GenreDto> = listOf()
)
