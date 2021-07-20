package com.example.pixabayapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    var pixabayRepo: PixabayRepo? = null
    val searchResultFlow = MutableStateFlow<List<ImageSummaryViewData>>(emptyList())

    data class ImageSummaryViewData(
        var url: String = "",
        var photographer: String? = "",
    )

    private fun pixabayImageToImageSummaryView(pixabayImage: PixabayResponse.PixabayImage): ImageSummaryViewData {
        return ImageSummaryViewData(
            pixabayImage.src.small,
            pixabayImage.photographer,
        )
    }

    fun searchImage(query: String) {
        viewModelScope.launch {
            val results = pixabayRepo?.search(query)
                if (results != null && results.isSuccessful) {
                    val images = results.body()?.photos

                        searchResultFlow.value = (images?.map {image ->
                            pixabayImageToImageSummaryView(image)
                        } ?: emptyList())

                }

        }
    }
}