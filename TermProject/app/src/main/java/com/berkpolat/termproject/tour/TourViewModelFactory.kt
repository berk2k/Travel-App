package com.berkpolat.termproject.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao

class TourViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourViewModel::class.java)) {
            return TourViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}