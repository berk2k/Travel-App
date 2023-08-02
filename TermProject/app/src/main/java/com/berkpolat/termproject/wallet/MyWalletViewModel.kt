package com.berkpolat.termproject.wallet

import androidx.lifecycle.ViewModel
import com.berkpolat.termproject.User
import com.berkpolat.termproject.interfaces.AppDao


class MyWalletViewModel(val dao: AppDao) : ViewModel() {


    fun getUser(userName: String): User {
        val user = dao.getUserByUserName(userName)
        return user!!
    }
}