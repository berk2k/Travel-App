package com.berkpolat.termproject.bookDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao

class BookedTourViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookedTourViewModel::class.java)) {
            return BookedTourViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}