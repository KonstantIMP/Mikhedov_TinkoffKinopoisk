package org.kimp.kinopoisk.tinkoff.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.kimp.kinopoisk.tinkoff.data.remote.api.KinopoiskApi
import org.kimp.kinopoisk.tinkoff.util.AuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideKinopoiskApi(
        retrofit: Retrofit
    ) = retrofit.create(KinopoiskApi::class.java)


    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
}