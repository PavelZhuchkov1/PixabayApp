package com.example.pixabayapp.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
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
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search), ImageListAdapter.ImageListAdapterListener {

    val TAG = javaClass.simpleName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels{
        factory.create()
    }

    @Inject
    lateinit var factory: ViewModelFactory.Factory
    private lateinit var imageListAdapter: ImageListAdapter

    companion object {
        fun newInstance(query: String) : SearchFragment {
            val args = Bundle()
            args.putString("query", query)
            val searchFragment = SearchFragment()
            searchFragment.arguments = args
            return searchFragment
        }
    }

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
        updateControls()
        val query: String = arguments?.getString("query").orEmpty()
        searchViewModel.onQueryChange(query)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.explode)
        }
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
                        is Error.ConnectionError -> Snackbar.make(binding.recyclerView, "No Connection", Snackbar.LENGTH_SHORT).show()
                        is Error.AuthorizationError -> Snackbar.make(binding.recyclerView, "Authorization error", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    override fun onShowDetails(imageSummaryViewData: SearchViewModel.ImageSummaryViewData, imageView: ImageView) {

        Utils.hideKeyboardFrom(context, this.view?.rootView)
        val imageFragment = ImageFragment.newInstance(imageSummaryViewData, ViewCompat.getTransitionName(imageView)!!)
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            addSharedElement(imageView, ViewCompat.getTransitionName(imageView)!!)
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