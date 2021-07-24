package com.example.pixabayapp

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "563492ad6f9170000100000102d7eaa21fb447e9a40e6a64eaefe65c")
            .build()
        )
    }
}