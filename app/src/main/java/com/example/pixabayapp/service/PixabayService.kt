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

    //@Headers("Authorization: 563492ad6f9170000100000102d7eaa21fb447e9a40e6a64eaefe65c")
    @GET("/v1/search")
    suspend fun searchImage(@Query("query") query: String): Response<PixabayResponse>
}