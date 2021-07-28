package com.example.pixabayapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pixabayapp.search.repository.SearchRepo
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val searchRepo: SearchRepo,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchRepo) as T
    }

    class Factory @Inject constructor(val searchRepo: SearchRepo) {

        fun create(): ViewModelFactory {
            return ViewModelFactory(searchRepo)
        }
    }

}