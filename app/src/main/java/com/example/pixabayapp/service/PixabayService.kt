package com.example.pixabayapp.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("/api/?key=22450368-6657e5796f35eecf6a114874a&image_type=photo")
    suspend fun searchImageByTerm(@Query("q") query: String): Response<PixabayResponse>

    companion object {

        val instance: PixabayService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pixabay.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(PixabayService::class.java)
        }
    }
}