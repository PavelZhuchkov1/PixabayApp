package com.example.pixabayapp.service

data class PixabayResponse(
    val total: Int,
    val hits: List<PixabayImage>) {

    data class PixabayImage(
        val previewURL: String,
        val tags: String,
        val likes: String
        )
}
