package org.kimp.kinopoisk.tinkoff.data.remote.dto

data class TopFilmsDto(
    val pagesCount: Int,
    val films: List<FilmTopResponseDto>
)
