package org.kimp.kinopoisk.tinkoff.data.remote.dto

data class FilmDto(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int?,
    val description: String?,
    val genres: List<GenreDto> = listOf(),
    val countries: List<CountryDto> = listOf()
)
