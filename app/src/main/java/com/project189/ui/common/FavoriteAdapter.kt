package com.project189.ui.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.project189.data.local.FavoriteEntity
import com.project189.data.model.TourItem
import com.project189.databinding.ItemFavoriteBinding
import com.project189.ui.detail.DetailActivity
import com.project189.utils.Constants
import com.project189.utils.loadImage

class FavoriteAdapter(
    private val onRemove: (TourItem) -> Unit
) : ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: FavoriteEntity) {
            binding.ivTourImage.loadImage(entity.pic)
            binding.tvTitle.text = entity.title
            binding.tvAddress.text = entity.address
            binding.tvPrice.text = "$${entity.price.toInt()}"
            binding.tvRating.text = entity.score.toString()

            val tourItem = entity.toTourItem()

            binding.btnRemove.setOnClickListener { onRemove(tourItem) }

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(Constants.EXTRA_TOUR_JSON, Gson().toJson(tourItem))
                }
                context.startActivity(intent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(old: FavoriteEntity, new: FavoriteEntity) = old.title == new.title
        override fun areContentsTheSame(old: FavoriteEntity, new: FavoriteEntity) = old == new
    }

    private fun FavoriteEntity.toTourItem() = TourItem(
        title = title,
        address = address,
        pic = pic,
        price = price,
        score = score,
        distance = distance,
        duration = duration,
        bed = bed,
        dateTour = dateTour,
        timeTour = timeTour,
        description = description,
        tourGuideName = tourGuideName,
        tourGuidePhone = tourGuidePhone,
        tourGuidePic = tourGuidePic
    )
}
