package com.poklad.giphyjob.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_NAME, apiKey)
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }

    companion object {
        private const val API_KEY_NAME = "api_key"
    }
}