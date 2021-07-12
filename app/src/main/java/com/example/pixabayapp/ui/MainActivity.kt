package com.example.pixabayapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixabayapp.R
import com.example.pixabayapp.adapter.ImageListAdapter
import com.example.pixabayapp.databinding.ActivityMainBinding
import com.example.pixabayapp.repository.PixabayRepo
import com.example.pixabayapp.service.PixabayService
import com.example.pixabayapp.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), ImageListAdapter.ImageListAdapterListener {

    val TAG = javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupViewModels()
        updateControls()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu.findItem(R.id.search_item)
        val searchView = searchMenuItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true

    }

    private fun performSearch(query: String) {
        showProgressBar()
        GlobalScope.launch {
            val results = searchViewModel.searchImage(query)
            withContext(Dispatchers.Main) {
                hideProgressBar()
                binding.toolbar.title = query
                imageListAdapter.setSearchData(results)
            }
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY) ?: return
            performSearch(query)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupViewModels() {
        val service = PixabayService.instance
        searchViewModel.pixabayRepo = PixabayRepo(service)
    }

    private fun updateControls() {
        binding.recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,
            layoutManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        imageListAdapter = ImageListAdapter(null, this, this)
        binding.recyclerView.adapter = imageListAdapter
    }

    override fun onShowDetails(imageSummaryViewData: SearchViewModel.ImageSummaryViewData) {

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}