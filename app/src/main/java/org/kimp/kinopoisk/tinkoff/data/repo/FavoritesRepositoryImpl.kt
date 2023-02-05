package org.kimp.kinopoisk.tinkoff.data.repo

import org.kimp.kinopoisk.tinkoff.data.base.FilmEntity
import org.kimp.kinopoisk.tinkoff.data.base.FilmsDao
import org.kimp.kinopoisk.tinkoff.data.remote.dto.CountryDto
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.data.remote.dto.GenreDto
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val filmsDao: FilmsDao
): FavoritesRepository {
    override fun getFilms(): List<FilmDto> {
        return filmsDao.getAll()
            .map {
                return@map FilmDto(
                    it.filmId, it.name, it.poster, it.preview, it.year, it.description,
                    it.genres.map { x -> GenreDto(x) }.toList(),
                    it.countries.map { x -> CountryDto(x) }.toList()
                )
            }.toList()
    }

    override fun addFilm(filmDto: FilmDto) {
        filmsDao.insertAll(
            FilmEntity(
                filmDto.kinopoiskId, filmDto.nameRu, filmDto.posterUrl, filmDto.posterUrlPreview,
                filmDto.year!!, filmDto.description!!,
                filmDto.genres.map { x -> x.genre }.toList(),
                filmDto.countries.map { x -> x.country }.toList()
            )
        )
    }

    override fun removeFilm(id: Int) {
       filmsDao.deleteById(id)
    }
}