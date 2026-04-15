package com.project189.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val title: String,
    val address: String,
    val pic: String,
    val price: Double,
    val score: Double,
    val distance: String,
    val duration: String,
    val bed: Int,
    val dateTour: String,
    val timeTour: String,
    val description: String,
    val tourGuideName: String,
    val tourGuidePhone: String,
    val tourGuidePic: String
)
