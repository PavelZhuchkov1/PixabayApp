package com.example.pixabayapp.service

//data class PixabayResponse(
//    val photos: List<PixabayImage>) {
//    data class PixabayImage(
//        val url: String,
//        val photographer: String
//        )
//}

data class PixabayResponse(val photos: List<PixabayImage>) {
    data class PixabayImage(
        val url: String,
        val photographer: String,
        var src: ImageFile) {
        data class ImageFile(val small: String)
    }
}
