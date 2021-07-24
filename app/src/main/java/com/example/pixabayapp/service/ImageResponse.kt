package com.example.pixabayapp.service

data class ImageResponse(val photos: List<SearchImage>) {
    data class SearchImage(
        val url: String,
        val photographer: String,
        var src: ImageFile) {
        data class ImageFile(val small: String)
    }
}
