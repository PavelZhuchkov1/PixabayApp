package com.example.pixabayapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.viewmodel.SearchViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val pixabayRepo: PixabayRepo,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SearchViewModel(pixabayRepo) as T
    }

}