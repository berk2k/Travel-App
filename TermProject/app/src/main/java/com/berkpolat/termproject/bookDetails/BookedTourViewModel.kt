package com.berkpolat.termproject.bookDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.berkpolat.termproject.User
import com.berkpolat.termproject.interfaces.AppDao
import kotlinx.coroutines.launch

class BookedTourViewModel(val dao: AppDao) : ViewModel() {
    lateinit var bookedTours: LiveData<List<Book>>
    lateinit var book: Book
    lateinit var user: User
    var returnMoney = 0F

    private val _deleteTour = MutableLiveData<Int?>()
    val deleteTour: LiveData<Int?>
        get() = _deleteTour

    fun saveTour(userName: String): LiveData<List<Book>> {
        bookedTours = dao.getBookedToursByUser(userName)
        return bookedTours
    }


    fun onDeleteClicked(bookId: Int, userName: String) {
        book = dao.getBookedTourById(bookId)!!
        user = dao.getUserByUserName(userName)!!
        viewModelScope.launch {
            val cost = dao.getCostById(bookId)
            returnMoney = cost!!
            user.account_money = user.account_money + cost!!
            dao.updateUser(user)
            dao.deleteBook(book)
            _deleteTour.value = bookId
        }
    }

    fun onTourDeleted() {
        _deleteTour.value = null
    }

}