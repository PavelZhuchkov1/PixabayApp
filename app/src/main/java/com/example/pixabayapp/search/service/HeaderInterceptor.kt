package com.example.pixabayapp.search.service

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "")
            .build()
        )
    }
}