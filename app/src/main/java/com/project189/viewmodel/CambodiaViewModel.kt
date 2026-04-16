package com.project189.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project189.data.model.TourItem
import com.project189.data.repository.TourRepository
import kotlinx.coroutines.launch

class CambodiaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TourRepository(application)
    private var allDestinations: List<TourItem> = emptyList()

    private val _items = MutableLiveData<List<TourItem>>()
    val items: LiveData<List<TourItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadAllDestinations()
    }

    private fun loadAllDestinations() {
        viewModelScope.launch {
            _isLoading.value = true
            allDestinations = repository.getAllItems()
            _items.value = allDestinations
            _isLoading.value = false
        }
    }

    fun filterByCategory(category: String) {
        if (category == "All") {
            _items.value = allDestinations
        } else {
            // Filter destinations by description or address since there's no explicit category field in TourItem
            _items.value = allDestinations.filter { 
                it.description.contains(category, ignoreCase = true) || 
                it.address.contains(category, ignoreCase = true)
            }
        }
    }
}
