package com.example.pixabayapp.search.repository

import com.example.pixabayapp.search.service.ImageResponse
import com.example.pixabayapp.search.service.SearchService
import java.net.UnknownHostException

class SearchRepo (private val searchService: SearchService) {

    suspend fun search(query: String): ImageResponse {
        return searchService.searchImage(query)
    }
}