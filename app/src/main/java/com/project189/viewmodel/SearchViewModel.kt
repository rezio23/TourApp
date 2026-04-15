package com.project189.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project189.data.model.TourItem
import com.project189.data.repository.TourRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TourRepository(application)

    private val _results = MutableLiveData<List<TourItem>>()
    val results: LiveData<List<TourItem>> = _results

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }
        searchJob = viewModelScope.launch {
            delay(300) // debounce
            _isLoading.value = true
            _results.value = repository.searchItems(query)
            _isLoading.value = false
        }
    }

    fun loadAll() {
        viewModelScope.launch {
            _isLoading.value = true
            val all = repository.getAllItems() + repository.getPopularItems()
            _results.value = all.distinctBy { it.title }
            _isLoading.value = false
        }
    }
}
