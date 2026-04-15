package com.project189.data.model

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("url") val url: String
)

data class Category(
    @SerializedName("Id") val id: Int,
    @SerializedName("ImagePath") val imagePath: String,
    @SerializedName("Name") val name: String
)

data class TourItem(
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("price") val price: Double,
    @SerializedName("score") val score: Double,
    @SerializedName("distance") val distance: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("bed") val bed: Int,
    @SerializedName("dateTour") val dateTour: String,
    @SerializedName("timeTour") val timeTour: String,
    @SerializedName("tourGuideName") val tourGuideName: String,
    @SerializedName("tourGuidePhone") val tourGuidePhone: String,
    @SerializedName("tourGuidePic") val tourGuidePic: String
)

data class Location(
    @SerializedName("Id") val id: Int,
    @SerializedName("loc") val loc: String
)

data class TourDatabase(
    @SerializedName("Banner") val banners: List<Banner>,
    @SerializedName("Category") val categories: List<Category>,
    @SerializedName("Item") val items: List<TourItem>,
    @SerializedName("Popular") val popular: List<TourItem>,
    @SerializedName("Location") val locations: List<Location>
)
