package com.example.pixabayapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixabayapp.databinding.SearchItemBinding
import com.example.pixabayapp.viewmodel.SearchViewModel

class ImageListAdapter(
    private var imageSummaryViewList: List<SearchViewModel.ImageSummaryViewData>?,
    private val imageListAdapterListener: ImageListAdapterListener,
    private val parentActivity: Activity
) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    interface ImageListAdapterListener {
        fun onShowDetails(imageSummaryViewData: SearchViewModel.ImageSummaryViewData)
    }

    inner class ViewHolder(
    databinding: SearchItemBinding,
    private val imageListAdapterListener: ImageListAdapterListener
    ) : RecyclerView.ViewHolder(databinding.root) {
        var imageSummaryViewData: SearchViewModel.ImageSummaryViewData? = null
        val tagsTextView: TextView = databinding.tags
        val likesTextView: TextView = databinding.likes
        val imageImageView : ImageView = databinding.image

        init {
            databinding.searchItem.setOnClickListener {
                imageSummaryViewData?.let {
                    imageListAdapterListener.onShowDetails(it)
                }
            }
        }
    }

    fun setSearchData(imageSummaryViewData: List<SearchViewModel.ImageSummaryViewData>) {
        imageSummaryViewList = imageSummaryViewData
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),
            imageListAdapterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchViewList = imageSummaryViewList ?: return
        val searchView = searchViewList[position]
        holder.imageSummaryViewData = searchView
        holder.tagsTextView.text = searchView.tags
        holder.likesTextView.text = searchView.likes
        Glide.with(parentActivity)
            .load(searchView.previewUrl)
            .into(holder.imageImageView)
    }

    override fun getItemCount(): Int {
        return imageSummaryViewList?.size ?: 0
    }
}