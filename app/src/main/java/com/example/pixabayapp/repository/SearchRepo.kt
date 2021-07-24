package com.example.pixabayapp.repository

import com.example.pixabayapp.service.SearchService

class SearchRepo (private val searchService: SearchService) {

    suspend fun search(query: String) = searchService.searchImage(query)
}