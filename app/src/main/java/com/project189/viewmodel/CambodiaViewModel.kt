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

    private val _items = MutableLiveData<List<TourItem>>()
    val items: LiveData<List<TourItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadCambodiaDestinations()
    }

    private fun loadCambodiaDestinations() {
        viewModelScope.launch {
            _isLoading.value = true
            val allItems = repository.getAllItems()
            _items.value = allItems.filter { it.address.contains("Cambodia", ignoreCase = true) }
            _isLoading.value = false
        }
    }
}
