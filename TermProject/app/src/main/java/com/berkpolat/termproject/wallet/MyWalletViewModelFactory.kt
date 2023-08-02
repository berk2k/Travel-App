package com.berkpolat.termproject.wallet


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.interfaces.AppDao



class MyWalletViewModelFactory(private val dao: AppDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyWalletViewModel::class.java)) {
            return MyWalletViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}