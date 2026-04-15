package com.project189.ui.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.project189.data.model.TourItem
import com.project189.databinding.ItemTourBinding
import com.project189.ui.detail.DetailActivity
import com.project189.utils.Constants
import com.project189.utils.loadImage

class TourAdapter : ListAdapter<TourItem, TourAdapter.TourViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val binding = ItemTourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TourViewHolder(private val binding: ItemTourBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TourItem) {
            binding.ivTourImage.loadImage(item.pic)
            binding.tvTitle.text = item.title
            binding.tvAddress.text = item.address
            binding.tvPrice.text = "$${item.price.toInt()}"
            binding.tvRating.text = item.score.toString()
            binding.tvDuration.text = item.duration

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(Constants.EXTRA_TOUR_JSON, Gson().toJson(item))
                }
                context.startActivity(intent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TourItem>() {
        override fun areItemsTheSame(old: TourItem, new: TourItem) = old.title == new.title
        override fun areContentsTheSame(old: TourItem, new: TourItem) = old == new
    }
}
