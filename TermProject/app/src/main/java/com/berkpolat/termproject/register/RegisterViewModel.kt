package com.berkpolat.termproject.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.User
import com.berkpolat.termproject.interfaces.AppDao
import kotlinx.coroutines.launch


class RegisterViewModel(val dao: AppDao) : ViewModel() {
    lateinit var user: User

    sealed class RegisterResult {
        object Valid : RegisterResult()
        object InvalidEmail : RegisterResult()
        object EmptyFields : RegisterResult()
        object PasswordNotMatch : RegisterResult()
        object UserNameTaken : RegisterResult()
        object EmailTaken : RegisterResult()
    }

    private var _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult>
        get() = _registerResult

    fun register(userName: String, email: String, password: String, password2: String) {
        if (userName.isBlank() || email.isBlank() || password.isBlank() || password2.isBlank()) {
            _registerResult.value = RegisterResult.EmptyFields
            return
        }
        if (!isValidEmail(email)) {
            _registerResult.value = RegisterResult.InvalidEmail
            return
        }
        if (isUserNameTaken(userName)) {
            _registerResult.value = RegisterResult.UserNameTaken
            return
        }
        if (isEmailTaken(email)) {
            _registerResult.value = RegisterResult.EmailTaken
            return
        }
        if (password != password2) {
            _registerResult.value = RegisterResult.PasswordNotMatch
            return
        }

        viewModelScope.launch {
            user = User()
            user.user_name = userName
            user.email = email
            user.password = password


            dao.insertUser(user)
            _registerResult.value = RegisterResult.Valid

        }

    }

    private fun isUserNameTaken(userName: String): Boolean {

        val userNames = dao.getAllUserNames()

        for (i in userNames.indices) {
            if (userName == userNames[i]) {
                return true
            }
        }
        return false

    }

    private fun isEmailTaken(email: String): Boolean {

        val mails = dao.getAllEmails()

        for (i in mails.indices) {
            if (email == mails[i]) {
                return true
            }
        }
        return false

    }


    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}