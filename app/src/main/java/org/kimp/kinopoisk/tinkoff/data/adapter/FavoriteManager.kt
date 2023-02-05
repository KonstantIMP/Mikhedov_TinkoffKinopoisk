package org.kimp.kinopoisk.tinkoff.data.adapter

import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto

interface FavoriteManager {
    fun isFavorite(id: Int): Boolean

    fun toggleFavorite(film: FilmDto)
}