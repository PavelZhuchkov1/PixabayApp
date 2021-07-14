package com.example.pixabayapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class SearchViewModel : ViewModel() {
    var pixabayRepo: PixabayRepo? = null
    val searchResultFlow = MutableStateFlow<List<ImageSummaryViewData>>(emptyList())

    data class ImageSummaryViewData(
        var previewUrl: String? = "",
        var tags: String? = "",
        var likes: String? = ""
    )

    private fun pixabayImageToImageSummaryView(pixabayImage: PixabayResponse.PixabayImage): ImageSummaryViewData {
        return ImageSummaryViewData(
            pixabayImage.previewURL,
            pixabayImage.tags,
            pixabayImage.likes
        )
    }

    fun searchImage(query: String) {
        viewModelScope.launch {
            val results = pixabayRepo?.search(query)
                if (results != null && results.isSuccessful) {
                    val images = results.body()?.hits

                        searchResultFlow.value = (images?.map {image ->
                            pixabayImageToImageSummaryView(image)
                        } ?: emptyList())

                }

        }
    }
}