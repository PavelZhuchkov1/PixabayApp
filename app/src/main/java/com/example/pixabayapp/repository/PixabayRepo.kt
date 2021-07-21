package com.example.pixabayapp.repository

import com.example.pixabayapp.service.PixabayService
import javax.inject.Inject

class PixabayRepo @Inject constructor(private val pixabayService: PixabayService) {

    suspend fun search(query: String) = pixabayService.searchImage(query)
}