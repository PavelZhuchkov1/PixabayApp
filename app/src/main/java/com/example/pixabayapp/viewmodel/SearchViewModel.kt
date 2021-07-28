package com.example.pixabayapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.Error
import com.example.pixabayapp.search.repository.SearchRepo
import com.example.pixabayapp.search.service.ImageResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class SearchViewModel(private val searchRepo: SearchRepo) : ViewModel() {
    private val _searchResultFlow = MutableStateFlow<List<ImageSummaryViewData>>(emptyList())
    val searchResultFlow = _searchResultFlow.asStateFlow()
    val errorFlow = MutableSharedFlow<Error>()

    data class ImageSummaryViewData(
        var original: String = "",
        var small: String = "",
        var photographer: String? = "",
    )

    private fun pixabayImageToImageSummaryView(searchImage: ImageResponse.SearchImage): ImageSummaryViewData {
        return ImageSummaryViewData(
            searchImage.src.original,
            searchImage.src.small,
            searchImage.photographer,
        )
    }

    fun searchImage(query: String) {
        viewModelScope.launch {
            try {
                val results = searchRepo.search(query)
                val images = results.photos
                _searchResultFlow.value = (images.map {image ->
                    pixabayImageToImageSummaryView(image)})
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException -> errorFlow.emit(Error.ConnectionError(message = "No Connection", e))
                    is HttpException -> errorFlow.emit(Error.AuthorizationError(message = "Authorization error", e))
                }
            }
        }
    }
}