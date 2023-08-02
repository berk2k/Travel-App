package com.berkpolat.termproject.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.berkpolat.termproject.interfaces.AppDao


class CardViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            return CardViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}