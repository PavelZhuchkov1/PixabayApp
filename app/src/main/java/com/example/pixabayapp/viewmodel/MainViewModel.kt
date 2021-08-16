package com.example.pixabayapp.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.ui.EmptyFragment
import com.example.pixabayapp.ui.SearchFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val TAG = "MainViewModel"
    private val _fragmentStateFlow = MutableStateFlow<Fragment>(EmptyFragment())
    val fragmentStateFlow = _fragmentStateFlow.asStateFlow()

    fun changeFragment(query: String) {
        viewModelScope.launch {
            _fragmentStateFlow.value =
            if (query.length >= 3) {
                SearchFragment.newInstance(query)
            } else {
                EmptyFragment()
            }
        }
    }
}