package com.example.pixabayapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.viewmodel.MainViewModel.FragmentType.EMPTY
import com.example.pixabayapp.viewmodel.MainViewModel.FragmentType.SEARCH
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val TAG = "MainViewModel"
    private val _fragmentStateFlow = MutableStateFlow(EMPTY)
    val fragmentStateFlow = _fragmentStateFlow.asStateFlow()

    fun changeFragment(query: String) {
        viewModelScope.launch {
            _fragmentStateFlow.value =
            if (query.length >= 3) {
                SEARCH
            } else {
                EMPTY
            }
        }
    }

    enum class FragmentType {
        EMPTY, SEARCH
    }
}