package com.berkpolat.termproject.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao


class RegisterViewModelFactory(private val dao: AppDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}