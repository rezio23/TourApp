package com.project189.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project189.data.model.*
import com.project189.data.repository.TourRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TourRepository(application)

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _allItems = MutableLiveData<List<TourItem>>()
    val allItems: LiveData<List<TourItem>> = _allItems

    private val _popularItems = MutableLiveData<List<TourItem>>()
    val popularItems: LiveData<List<TourItem>> = _popularItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _banners.value = repository.getBanners()
            _categories.value = repository.getCategories()
            _allItems.value = repository.getAllItems()
            _popularItems.value = repository.getPopularItems()
            _isLoading.value = false
        }
    }
}
