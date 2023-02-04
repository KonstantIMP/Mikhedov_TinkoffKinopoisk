package org.kimp.kinopoisk.tinkoff.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.kimp.kinopoisk.tinkoff.data.remote.api.KinopoiskApi
import org.kimp.kinopoisk.tinkoff.data.remote.api.KinopoiskTopFilmsRequestType
import org.kimp.kinopoisk.tinkoff.data.remote.dto.TopFilmsDto
import org.kimp.kinopoisk.tinkoff.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NavigationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}