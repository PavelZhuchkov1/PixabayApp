package com.example.pixabayapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.pixabayapp.R
import com.example.pixabayapp.viewmodel.ViewModelFactory
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.FragmentImageBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import javax.inject.Inject

class ImageFragment(val imageSummaryViewData: SearchViewModel.ImageSummaryViewData) : Fragment(R.layout.fragment_image) {

    private lateinit var binding: FragmentImageBinding
    private val searchViewModel: SearchViewModel by viewModels{
        factory.create()
    }

    @Inject
    lateinit var factory: ViewModelFactory.Factory
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as SearchActivity).appComponent.inject2(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(imageSummaryViewData.original)
            .into(binding.image)
    }
}