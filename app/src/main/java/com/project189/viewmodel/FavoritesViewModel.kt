package com.project189.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project189.data.local.FavoriteEntity
import com.project189.data.model.TourItem
import com.project189.data.repository.TourRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TourRepository(application)

    val favorites: LiveData<List<FavoriteEntity>> = repository.getFavorites()

    fun removeFavorite(item: TourItem) {
        viewModelScope.launch {
            repository.removeFavorite(item)
        }
    }
}
