package com.example.pixabayapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixabayapp.Error
import com.example.pixabayapp.R
import com.example.pixabayapp.viewmodel.ViewModelFactory
import com.example.pixabayapp.ui.adapter.ImageListAdapter
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.FragmentSearchBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import com.example.pixabayapp.viewmodel.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment() : Fragment(R.layout.fragment_search), ImageListAdapter.ImageListAdapterListener {

    val TAG = javaClass.simpleName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels{
        factory.create()
    }

    @Inject
    lateinit var factory: ViewModelFactory.Factory
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as SearchActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        updateControls()
        lifecycleScope.launchWhenCreated {
            launch {
                searchViewModel.searchResultFlow.collectLatest {
                    hideProgressBar()
                    imageListAdapter.setSearchData(it)
                }
            }
            launch {
                searchViewModel.errorFlow.collectLatest {
                    Log.d(TAG, "Error: $it")
                    when (it) {
                        is Error.ConnectionError -> Snackbar.make(binding.editText, "No Connection", Snackbar.LENGTH_SHORT).show()
                        is Error.AuthorizationError -> Snackbar.make(binding.editText, "Authorization error", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 3) searchViewModel.onQueryChange(s.toString())
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
        binding.recyclerView.apply {
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(this.context, linearLayoutManager.orientation))
            imageListAdapter = ImageListAdapter(null,
                this@SearchFragment, this@SearchFragment)
            adapter = imageListAdapter
        }
    }

    override fun onShowDetails(imageSummaryViewData: SearchViewModel.ImageSummaryViewData) {
        Utils.hideKeyboardFrom(context, this.view?.rootView)
        val imageFragment = ImageFragment.newInstance(imageSummaryViewData)
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, imageFragment)
            addToBackStack(null)
        }

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}