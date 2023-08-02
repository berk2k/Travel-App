package com.berkpolat.termproject.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao

class BookTourViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookTourViewModel::class.java)) {
            return BookTourViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}