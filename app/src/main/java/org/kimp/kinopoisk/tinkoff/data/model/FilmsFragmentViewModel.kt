package org.kimp.kinopoisk.tinkoff.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.kimp.kinopoisk.tinkoff.data.FilmsFragmentMode
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.data.repo.FilmsRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FilmsFragmentViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
): ViewModel() {
    private val currentMode: MutableLiveData<FilmsFragmentMode> = MutableLiveData(FilmsFragmentMode.ShowPopularFilms)
    private val hasErrors: MutableLiveData<Boolean> = MutableLiveData(false)

    private val films: MutableLiveData<List<FilmDto>> = MutableLiveData()

    fun loadFilms() {
        viewModelScope.launch {
            hasErrors.value = false

            try {
                val response = async (Dispatchers.IO) {
                    filmsRepository.getFilms()
                }.await()
                if (response == null) cancel()
                Timber.i("Received %d films", response!!.size)
                films.value = response!!
            } catch (e: Exception) {
                Timber.e(e)
                hasErrors.value = true
            }
        }
    }

    fun currentMode(): LiveData<FilmsFragmentMode> = currentMode
    fun hasErrors(): LiveData<Boolean> = hasErrors
    fun films(): LiveData<List<FilmDto>> = films
}
