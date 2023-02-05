package org.kimp.kinopoisk.tinkoff.data.repo

import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto

interface FavoritesRepository {
    fun getFilms(): List<FilmDto>?

    fun addFilm(filmDto: FilmDto)

    fun removeFilm(id: Int)
}