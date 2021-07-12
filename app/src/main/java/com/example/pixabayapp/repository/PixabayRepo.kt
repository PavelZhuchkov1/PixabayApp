package com.example.pixabayapp.repository

import com.example.pixabayapp.service.PixabayService

class PixabayRepo(private val pixabayService: PixabayService) {

    suspend fun search(query: String) = pixabayService.searchImage(query)
}