package com.example.pixabayapp.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface PixabayService {

    @GET("/v1/search")
    suspend fun searchImage(@Query("query") query: String): Response<PixabayResponse>
}