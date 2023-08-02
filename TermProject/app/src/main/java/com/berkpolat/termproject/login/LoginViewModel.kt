package com.berkpolat.termproject.login


import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.User

import com.berkpolat.termproject.interfaces.AppDao
import kotlinx.coroutines.launch

class LoginViewModel(val dao: AppDao) : ViewModel() {
    lateinit var user: User

    sealed class LoginResult {
        object Valid : LoginResult()
        object InvalidEmail : LoginResult()
        object EmptyFields : LoginResult()
        object UserNotFound : LoginResult()
        object IncorrectPassword : LoginResult()
    }

    private var _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginResult.value = LoginResult.EmptyFields
            return
        }
        if (!isValidEmail(email)) {
            _loginResult.value = LoginResult.InvalidEmail
            return
        }

        if (!isUserExist(email)) {
            _loginResult.value = LoginResult.UserNotFound
            return
        }

        viewModelScope.launch {
            user = dao.getUserByEmail(email)!!

            if (user.password == password) {

                _loginResult.value = LoginResult.Valid
            } else {
                _loginResult.value = LoginResult.IncorrectPassword
            }

        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isUserExist(email: String): Boolean {
        val mails = dao.getAllEmails()

        for (i in mails.indices) {
            if (email == mails[i]) {
                return true
            }
        }
        return false
    }


}