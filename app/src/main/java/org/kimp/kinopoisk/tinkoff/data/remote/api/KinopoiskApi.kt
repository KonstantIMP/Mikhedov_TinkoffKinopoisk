package org.kimp.kinopoisk.tinkoff.data.remote.api

import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.data.remote.dto.TopFilmsDto
import org.kimp.kinopoisk.tinkoff.util.Authenticated
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("/api/v2.2/films/{id}")
    @Authenticated
    fun getFilm(@Path("id") filmId: Int): Call<FilmDto>

    @GET("/api/v2.2/films/top")
    @Authenticated
    fun getTopFilms(
        @Query("page") page: Int,
        @Query("type") type: KinopoiskTopFilmsRequestType
    ): Call<TopFilmsDto>
}
