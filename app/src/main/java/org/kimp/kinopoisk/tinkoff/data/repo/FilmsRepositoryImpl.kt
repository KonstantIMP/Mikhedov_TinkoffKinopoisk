package org.kimp.kinopoisk.tinkoff.data.repo

import org.kimp.kinopoisk.tinkoff.data.remote.api.KinopoiskApi
import org.kimp.kinopoisk.tinkoff.data.remote.api.KinopoiskTopFilmsRequestType
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmTopResponseDto
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    val kinopoiskApi: KinopoiskApi
) : FilmsRepository {
    override fun getFilms(): List<FilmDto>? {
        try {
            val top100 = getTop100Films() ?: return null
            return top100.asSequence()
                .map { x -> kinopoiskApi.getFilm(x.filmId) }
                .map { x -> x.execute() }
                .apply {
                    forEach { x -> if (!x.isSuccessful) return@getFilms null }
                }
                .map { x -> x.body()!! }
                .toList()
        } catch (e: Exception) { return null }
    }

    private fun getTop100Films(): ArrayList<FilmTopResponseDto>? {
        val result = ArrayList<FilmTopResponseDto>()

        var currentPage: Int = 1
        while (result.size != 100) {
            kinopoiskApi.getTopFilms(
                currentPage,
                KinopoiskTopFilmsRequestType.TOP_100_POPULAR_FILMS
            ).execute().apply {
                if (!isSuccessful) return@getTop100Films null
                result.addAll(body()!!.films)
                currentPage++
            }
        }
        return result
    }
}