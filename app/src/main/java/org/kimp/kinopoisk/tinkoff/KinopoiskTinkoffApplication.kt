package org.kimp.kinopoisk.tinkoff

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.kimp.kinopoisk.tinkoff.data.model.FavoriteViewModel
import timber.log.Timber

@HiltAndroidApp
class KinopoiskTinkoffApplication: Application() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())

        super.onCreate()
    }
}