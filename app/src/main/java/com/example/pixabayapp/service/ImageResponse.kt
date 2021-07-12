package com.example.pixabayapp.service

data class PixabayResponse(
    val total: Int,
    val hits: List<PixabayImage>) {

    data class PixabayImage(
        val id: Int,
        val previewURL: String,
        val tags: String,
        val likes: Int
        )
}
