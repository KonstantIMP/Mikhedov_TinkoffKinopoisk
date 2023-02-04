package org.kimp.kinopoisk.tinkoff.data.repo

import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto

interface FilmsRepository {
    fun getFilms(): List<FilmDto>?
}