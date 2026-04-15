package com.project189.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.project189.R
import com.project189.data.model.TourItem
import com.project189.databinding.ActivityDetailBinding
import com.project189.ui.common.ContactBottomSheet
import com.project189.utils.Constants
import com.project189.utils.loadImage
import com.project189.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var tourItem: TourItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = intent.getStringExtra(Constants.EXTRA_TOUR_JSON) ?: run {
            finish(); return
        }
        tourItem = Gson().fromJson(json, TourItem::class.java)

        bindData()
        setupClickListeners()
        viewModel.checkFavorite(tourItem.title)
        observeFavorite()
    }

    private fun bindData() {
        binding.apply {
            ivTourImage.loadImage(tourItem.pic)
            tvTitle.text = tourItem.title
            tvAddress.text = tourItem.address
            tvRating.text = tourItem.score.toString()
            tvDescription.text = tourItem.description
            tvBeds.text = "${tourItem.bed} Beds"
            tvDistance.text = tourItem.distance
            tvDuration.text = tourItem.duration
            tvDate.text = tourItem.dateTour
            tvTime.text = tourItem.timeTour
            tvPrice.text = "$${tourItem.price.toInt()}"
            tvGuideName.text = tourItem.tourGuideName
            ivGuidePhoto.loadImage(tourItem.tourGuidePic)
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite(tourItem)
        }

        binding.btnContact.setOnClickListener {
            val sheet = ContactBottomSheet.newInstance(tourItem.tourGuidePhone, tourItem.tourGuideName)
            sheet.show(supportFragmentManager, "contact")
        }

        binding.btnBookNow.setOnClickListener {
            // Future: navigate to booking flow
        }
    }

    private fun observeFavorite() {
        viewModel.isFavorite.observe(this) { isFav ->
            binding.btnFavorite.setImageResource(
                if (isFav) R.drawable.fav_icon else R.drawable.fav_icon
            )
            binding.btnFavorite.alpha = if (isFav) 1.0f else 0.4f
        }
    }
}
