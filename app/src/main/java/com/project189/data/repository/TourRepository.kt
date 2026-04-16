package com.project189.data.repository

import android.content.Context
import com.google.gson.Gson
import com.project189.data.local.AppDatabase
import com.project189.data.local.FavoriteEntity
import com.project189.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TourRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val favoriteDao = db.favoriteDao()

    private fun loadDatabase(): TourDatabase {
        val json = context.assets.open("database.json")
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(json, TourDatabase::class.java)
    }

    suspend fun getBanners(): List<Banner> = withContext(Dispatchers.IO) {
        loadDatabase().banners
    }

    suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
        loadDatabase().categories
    }

    suspend fun getAllItems(): List<TourItem> = withContext(Dispatchers.IO) {
        loadDatabase().items
    }

    suspend fun getPopularItems(): List<TourItem> = withContext(Dispatchers.IO) {
        loadDatabase().popular
    }

    suspend fun getLocations(): List<Location> = withContext(Dispatchers.IO) {
        loadDatabase().locations
    }

    suspend fun searchItems(query: String): List<TourItem> = withContext(Dispatchers.IO) {
        val all = loadDatabase().items + loadDatabase().popular
        all.distinctBy { it.title }
            .filter {
                it.title.contains(query, ignoreCase = true) ||
                it.address.contains(query, ignoreCase = true)
            }
    }

    fun getFavorites() = favoriteDao.getAllFavorites()

    suspend fun addFavorite(item: TourItem) {
        favoriteDao.insertFavorite(item.toEntity())
    }

    suspend fun removeFavorite(item: TourItem) {
        favoriteDao.deleteFavorite(item.toEntity())
    }

    suspend fun isFavorite(title: String) = favoriteDao.isFavorite(title)

    private fun TourItem.toEntity() = FavoriteEntity(
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
