package org.kimp.kinopoisk.tinkoff

import android.app.Application
import timber.log.Timber

class KinopoiskTinkoffApplication: Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}