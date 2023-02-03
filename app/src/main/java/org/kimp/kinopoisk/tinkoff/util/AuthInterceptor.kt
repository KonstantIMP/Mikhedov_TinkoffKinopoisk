package org.kimp.kinopoisk.tinkoff.util

import okhttp3.Interceptor
import okhttp3.Response
import org.kimp.kinopoisk.tinkoff.BuildConfig
import retrofit2.Invocation

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any{
                it.annotationClass == Authenticated::class
            }

        return if (shouldAttachAuthHeader) {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("X-API-KEY", BuildConfig.KINOPOISK_API_KEY)
                    .build()
            )
        } else chain.proceed(chain.request())
    }

}