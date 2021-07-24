package com.example.pixabayapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
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

class SearchActivity : AppCompatActivity(), ImageListAdapter.ImageListAdapterListener {

    val TAG = javaClass.simpleName

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels{
        factory.create()
    }

    @Inject
    lateinit var factory: ViewModelFactory.Factory
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        updateControls()
        lifecycleScope.launchWhenCreated {
            searchViewModel.searchResultFlow.collectLatest {
                hideProgressBar()
                imageListAdapter.setSearchData(it)
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
                if (s.length >= 3) performSearch(s.toString())
            }

        })
        //This code for press enter search
//        binding.editText.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                    performSearch(binding.editText.text.toString())
//                    return true
//                }
//                return false
//            }
//        })
    }

    private fun performSearch(query: String) {
        showProgressBar()
        searchViewModel.searchImage(query)

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