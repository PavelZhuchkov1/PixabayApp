package com.example.pixabayapp.search.repository

import com.example.pixabayapp.search.service.SearchService

class SearchRepo (private val searchService: SearchService) {

    suspend fun search(query: String) = searchService.searchImage(query)
}