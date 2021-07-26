package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixabayapp.R
import com.example.pixabayapp.ViewModelFactory
import com.example.pixabayapp.adapter.ImageListAdapter
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.ActivitySearchBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

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