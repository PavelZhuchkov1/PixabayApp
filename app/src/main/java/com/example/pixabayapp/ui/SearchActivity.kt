package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.pixabayapp.R
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.ActivitySearchBinding
import com.example.pixabayapp.viewmodel.MainViewModel
import com.example.pixabayapp.viewmodel.MainViewModel.FragmentType.EMPTY
import com.example.pixabayapp.viewmodel.MainViewModel.FragmentType.SEARCH
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(){

    val TAG = javaClass.simpleName
    private lateinit var binding: ActivitySearchBinding
    private val mainViewModel = MainViewModel()
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject3(this)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<EmptyFragment>(R.id.fragment_container_view)
            }
        }
        lifecycleScope.launchWhenCreated {
            launch {
                mainViewModel.fragmentStateFlow.collectLatest {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,
                                when(it) {
                                    EMPTY -> EmptyFragment()
                                    SEARCH -> SearchFragment.newInstance(query)
                                }
                            )
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                query = s.toString()
                mainViewModel.changeFragment(query)
            }
        })
    }
}