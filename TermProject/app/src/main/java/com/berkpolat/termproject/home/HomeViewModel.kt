package com.berkpolat.termproject.home

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.berkpolat.termproject.R
import com.berkpolat.termproject.interfaces.AppDao
import com.berkpolat.termproject.tour.Tour
import kotlinx.coroutines.launch


class HomeViewModel(private val dao: AppDao) : ViewModel() {
    val tours = dao.getAllTours()
    lateinit var tour: Tour


    private val _navigateToTourDetail = MutableLiveData<Int?>()
    val navigateToTourDetail: LiveData<Int?>
        get() = _navigateToTourDetail

    private val _searchResult = MutableLiveData<SearchResult>()
    val searchResult: LiveData<SearchResult>
        get() = _searchResult


    sealed class SearchResult {
        object Found : SearchResult()
        object EmptyField : SearchResult()
        object NotFound : SearchResult()
    }


    fun searchCity(destination: String) {

        if (destination.isBlank()) {
            _searchResult.value = SearchResult.EmptyField
            return
        }
        if (!isCityFound(destination)) {
            _searchResult.value = SearchResult.NotFound
            return
        }
        viewModelScope.launch {
            tour = dao.getTourByDestination(destination)!!
            _searchResult.value = SearchResult.Found
        }
    }

    private fun isCityFound(destination: String): Boolean {
        val destinations = dao.getAllDestinations()
        for (i in destinations.indices) {

            if (destination == destinations[i]) {
                return true
            }
        }
        return false
    }


    fun onTourClicked(tourId: Int) {
        _navigateToTourDetail.value = tourId

    }

    fun onTourNavigated() {
        _navigateToTourDetail.value = null

    }


}