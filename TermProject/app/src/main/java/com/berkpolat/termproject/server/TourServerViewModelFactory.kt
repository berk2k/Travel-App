package com.berkpolat.termproject.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao


class TourServerViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourServerViewModel::class.java)) {
            return TourServerViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}