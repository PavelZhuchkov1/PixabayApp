package com.example.pixabayapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.search.repository.SearchRepo
import com.example.pixabayapp.search.service.ImageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepo: SearchRepo) : ViewModel() {
    val searchResultFlow = MutableStateFlow<List<ImageSummaryViewData>>(emptyList())

    data class ImageSummaryViewData(
        var url: String = "",
        var photographer: String? = "",
    )

    private fun pixabayImageToImageSummaryView(searchImage: ImageResponse.SearchImage): ImageSummaryViewData {
        return ImageSummaryViewData(
            searchImage.src.small,
            searchImage.photographer,
        )
    }

    fun searchImage(query: String) {
        viewModelScope.launch {
            val results = searchRepo.search(query)
                if (results.isSuccessful) {
                    val images = results.body()?.photos

                        searchResultFlow.value = (images?.map {image ->
                            pixabayImageToImageSummaryView(image)
                        } ?: emptyList())

                }

        }
    }
}