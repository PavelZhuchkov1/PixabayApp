package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pixabayapp.R
import com.example.pixabayapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(){

    val TAG = javaClass.simpleName
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SearchFragment>(R.id.fragment_container_view)
            }
        }
    }
}