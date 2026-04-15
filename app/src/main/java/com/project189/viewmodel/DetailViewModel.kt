package com.project189.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project189.data.model.TourItem
import com.project189.data.repository.TourRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TourRepository(application)

    val isFavorite = MutableLiveData<Boolean>()

    fun checkFavorite(title: String) {
        viewModelScope.launch {
            isFavorite.value = repository.isFavorite(title)
        }
    }

    fun toggleFavorite(item: TourItem) {
        viewModelScope.launch {
            val current = repository.isFavorite(item.title)
            if (current) {
                repository.removeFavorite(item)
                isFavorite.value = false
            } else {
                repository.addFavorite(item)
                isFavorite.value = true
            }
        }
    }
}
