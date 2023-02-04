package org.kimp.kinopoisk.tinkoff.data.adapter

import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto

interface FilmSelectedListener {
    fun filmSelected(film: FilmDto)
}