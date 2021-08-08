package com.example.pixabayapp.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pixabayapp.R
import com.example.pixabayapp.appComponent
import com.example.pixabayapp.databinding.FragmentImageBinding
import com.example.pixabayapp.viewmodel.SearchViewModel
import kotlin.math.abs


class ImageFragment(val imageSummaryViewData: SearchViewModel.ImageSummaryViewData) : Fragment(R.layout.fragment_image){

    val TAG = javaClass.simpleName
    private lateinit var binding: FragmentImageBinding
    private var y = 0.0f
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
                    y = event.rawY
                    imageY = v.y
                }

                MotionEvent.ACTION_MOVE -> {
                    dy = event.rawY - y

                    v.y = v.y + dy

                    val diff = abs(imageY - v.y)
                    alpha = 1 - diff/imageY
                    if (alpha <= 0.0f) alpha = 0.0f
                    binding.root.background.alpha = (alpha * 255).toInt()

                    y = event.rawY

                }

                MotionEvent.ACTION_UP -> {

                    if (alpha <= 0.0f) {
                        activity?.supportFragmentManager?.popBackStackImmediate()
                    } else {
                        val changeAlpha = ValueAnimator.ofFloat(alpha, 1.0f)
                        changeAlpha.addUpdateListener {
                            val value = it.animatedValue as Float
                            binding.root.background.alpha = (value * 255).toInt()
                        }
                        changeAlpha.duration = 400

                        val moveImage = ValueAnimator.ofFloat(v.y, imageY)
                        moveImage.addUpdateListener {
                            val value = it.animatedValue as Float
                            v.y = value
                        }
                        moveImage.duration = 400

                        AnimatorSet().apply {
                            play(changeAlpha).with(moveImage)
                            start()
                        }
                    }
                }
            }

            Log.d(TAG, "onViewCreated: ${v.y}")

            true
        }
    }
}