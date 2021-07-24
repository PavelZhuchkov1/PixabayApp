package com.example.pixabayapp.search.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/v1/search")
    suspend fun searchImage(@Query("query") query: String): Response<ImageResponse>
}