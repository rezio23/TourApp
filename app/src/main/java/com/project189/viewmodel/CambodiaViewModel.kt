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
    private var currentCountry = "All"
    private var currentCategory = "All"

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
            applyFilters()
            _isLoading.value = false
        }
    }

    fun setCountry(country: String) {
        currentCountry = country
        applyFilters()
    }

    fun filterByCategory(category: String) {
        currentCategory = category
        applyFilters()
    }

    private fun applyFilters() {
        var filteredList = if (currentCountry == "All") {
            allDestinations.shuffled() // Randomize when "All" is selected
        } else {
            allDestinations.filter { it.address.contains(currentCountry, ignoreCase = true) }
        }

        if (currentCategory != "All") {
            filteredList = filteredList.filter {
                it.description.contains(currentCategory, ignoreCase = true) ||
                it.address.contains(currentCategory, ignoreCase = true)
            }
        }

        _items.value = filteredList
    }
}
