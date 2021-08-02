package com.example.pixabayapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pixabayapp.R
import com.example.pixabayapp.viewmodel.ViewModelFactory
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.FragmentImageBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import javax.inject.Inject

class ImageFragment(val imageSummaryViewData: SearchViewModel.ImageSummaryViewData) : Fragment(R.layout.fragment_image){

    val TAG = javaClass.simpleName
    private lateinit var binding: FragmentImageBinding
    private var x = 0.0f
    private var y = 0.0f
    private var dx = 0.0f
    private var dy = 0.0f
    
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(imageSummaryViewData.original)
            .into(binding.image)

        var alpha = 1.0f
        binding.root.background.alpha = (alpha * 255).toInt()
        var imageY = 0.0f
        binding.image.setOnTouchListener { v, event ->


            when(event.action) {

                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX
                    y = event.rawY
                    imageY = v.y
                }

                MotionEvent.ACTION_MOVE -> {
                    dx = event.rawX - x
                    dy = event.rawY - y

                    v.x = v.x + dx
                    if (v.y + dy >= 0) {
                        v.y = v.y + dy
                    }

                    alpha = v.y / imageY
                    if (alpha <= 1.0f) {
                        binding.root.background.alpha = (alpha * 255).toInt()
                    }

                    x = event.rawX
                    y = event.rawY

                }
            }

            true
        }
    }
}