package com.example.pixabayapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayResponse

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    var pixabayRepo: PixabayRepo? = null

    data class ImageSummaryViewData(
        var previewUrl: String? = "",
        var tags: String? = "",
        var likes: String? = "" //Int?
    )

    private fun pixabayImageToImageSummaryView(pixabayImage: PixabayResponse.PixabayImage): ImageSummaryViewData {
        return ImageSummaryViewData(
            pixabayImage.previewURL,
            pixabayImage.tags,
            pixabayImage.likes
        )
    }

    suspend fun searchImage(query: String): List<ImageSummaryViewData> {

        val results = pixabayRepo?.search(query)
        if (results != null && results.isSuccessful) {
            val images = results.body()?.hits
            if (!images.isNullOrEmpty()) {
                return images.map {image ->
                    pixabayImageToImageSummaryView(image)
                }
            }
        }
        return emptyList()
    }
}