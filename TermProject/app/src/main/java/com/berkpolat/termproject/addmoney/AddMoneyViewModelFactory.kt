package com.berkpolat.termproject.addmoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao

class AddMoneyViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMoneyViewModel::class.java)) {
            return AddMoneyViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}