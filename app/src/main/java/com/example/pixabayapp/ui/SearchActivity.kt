package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.pixabayapp.R
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.ActivitySearchBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import com.example.pixabayapp.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchActivity : AppCompatActivity(){

    val TAG = javaClass.simpleName
    private lateinit var binding: ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels{
        factory.create()
    }
    @Inject
    lateinit var factory: ViewModelFactory.Factory

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
    }

    private fun setupListeners() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 3) {
                    val searchFragment = SearchFragment.newInstance(s.toString())
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view, searchFragment)
                    }
                } else {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<EmptyFragment>(R.id.fragment_container_view)
                    }
                }

            }

        })
    }
}