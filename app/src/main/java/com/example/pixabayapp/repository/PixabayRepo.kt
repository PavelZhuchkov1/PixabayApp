package com.example.pixabayapp.repository

import com.example.pixabayapp.service.PixabayService

class PixabayRepo(private val pixabayService: PixabayService) {

    suspend fun searchByTerm(term: String) = pixabayService.searchImageByTerm(term)
}