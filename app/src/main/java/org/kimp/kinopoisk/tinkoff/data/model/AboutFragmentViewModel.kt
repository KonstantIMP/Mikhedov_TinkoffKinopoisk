package org.kimp.kinopoisk.tinkoff.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import javax.inject.Inject

@HiltViewModel
class AboutFragmentViewModel @Inject constructor(): ViewModel() {
    private val filmDto: MutableLiveData<FilmDto> = MutableLiveData()

    fun setFilm(film: FilmDto) {
        filmDto.value = film
    }

    fun film(): LiveData<FilmDto> = filmDto
}