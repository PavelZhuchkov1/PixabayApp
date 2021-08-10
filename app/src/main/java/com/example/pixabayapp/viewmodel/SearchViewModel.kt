package com.example.pixabayapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.Error
import com.example.pixabayapp.search.repository.SearchRepo
import com.example.pixabayapp.search.service.ImageResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class SearchViewModel(private val searchRepo: SearchRepo) : ViewModel() {
    val TAG = "SearchViewModel"
    private val _searchResultFlow = MutableStateFlow<List<ImageSummaryViewData>>(emptyList())
    val searchResultFlow = _searchResultFlow.asStateFlow()
    val errorFlow = MutableSharedFlow<Error>()
    val querySharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)

    init {
        viewModelScope.launch{
            querySharedFlow
                .debounce(300)
                .mapLatest { query ->
                    searchRepo.search(query)
                }
                .catch {
                    when (it) {
                        is UnknownHostException -> errorFlow.emit(Error.ConnectionError(message = "No Connection", it))
                        is HttpException -> errorFlow.emit(Error.AuthorizationError(message = "Authorization error", it))
                    }
                }
                .collect { response ->
                    val images = response.photos
                    _searchResultFlow.value = (images.map {image ->
                        pixabayImageToImageSummaryView(image)})
                }
        }
    }

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

    fun onQueryChange(query: String) {
        querySharedFlow.tryEmit(query)
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