package org.kimp.kinopoisk.tinkoff.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kimp.kinopoisk.tinkoff.data.adapter.FavoriteManager
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.data.repo.FavoritesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val favoritesRepository: FavoritesRepository
): ViewModel(), FavoriteManager {
    private val films = MutableLiveData<ArrayList<FilmDto>>(arrayListOf())

    fun loadFilms() {
        viewModelScope.launch {
            val response = async (Dispatchers.IO) {
                favoritesRepository.getFilms()!!
            }.await()
            films.value = ArrayList(response)
            Timber.i("Loaded ${response.size} entries")
        }
    }

    fun addFilm(film: FilmDto) {
        viewModelScope.launch {
            async( Dispatchers.IO) {
                favoritesRepository.addFilm(film)
            }.await()
            films.value = films.value!!.apply {
                add(film)
            }
        }
    }

    fun removeFilm(id: Int) {
        viewModelScope.launch {
            async( Dispatchers.IO )    {
                favoritesRepository.removeFilm(id)
            }    .await()
            films.value = ArrayList(films.value!!.filter { x -> x.kinopoiskId != id }.toList())

        }
    }

    fun films(): LiveData<ArrayList<FilmDto>> = films

    override fun isFavorite(id: Int): Boolean {
        return !films.value!!.none { x -> x.kinopoiskId == id }
    }

    override fun toggleFavorite(film: FilmDto) {
        if (isFavorite(film.kinopoiskId)) removeFilm(film.kinopoiskId)
        else addFilm(film)
    }
}