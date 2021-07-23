package com.example.pixabayapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.viewmodel.SearchViewModel
import dagger.assisted.AssistedInject
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val pixabayRepo: PixabayRepo,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(pixabayRepo) as T
    }

    class Factory @Inject constructor(val pixabayRepo: PixabayRepo) {

        fun create(): ViewModelFactory {
            return ViewModelFactory(pixabayRepo)
        }
    }

}