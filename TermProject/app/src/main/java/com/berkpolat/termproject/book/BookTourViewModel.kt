package com.berkpolat.termproject.book

import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.bookDetails.Book
import com.berkpolat.termproject.interfaces.AppDao
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch


class BookTourViewModel(val dao: AppDao) : ViewModel() {

    private var _totalFee = MutableLiveData<Float>(0f)
    val totalFee: LiveData<Float>
        get() = _totalFee

    private var _payResult = MutableLiveData<PayResult>()
    val payResult: LiveData<PayResult>
        get() = _payResult


    sealed class PayResult {
        object Valid : PayResult()
        object NotEnoughMoney : PayResult()
        object EmptyField : PayResult()
        object RadioNotSelected : PayResult()

    }


    fun getTotalFee(tourId: Int, textView: TextView) {
        val cost = dao.getCostByTourId(tourId)
        _totalFee.value = cost!! + textView.text.toString().toFloat()
    }

    fun pay(
        userName: String,
        checkBox: CheckBox,
        tourId: Int,
        radioButton: RadioButton,
        radioButton2: RadioButton,
        radioButton3: RadioButton
    ) {
        val user = dao.getUserByUserName(userName)
        if (user!!.account_money < _totalFee.value!!) {
            _payResult.value = PayResult.NotEnoughMoney
            return
        }
        if (!checkBox.isChecked) {
            _payResult.value = PayResult.EmptyField
            return
        }
        if (!isRadioButtonChecked(radioButton, radioButton2, radioButton3)) {
            _payResult.value = PayResult.RadioNotSelected

            return
        }
        viewModelScope.launch {
            val bookedTour = Book()
            val tour = dao.getTourByTourId(tourId)
            bookedTour.tourId = tour!!.tourId
            bookedTour.userName = userName
            bookedTour.destination = tour!!.destination
            bookedTour.date = tour!!.date
            bookedTour.hotelName = tour!!.hotelName
            bookedTour.cost = _totalFee.value!!
            dao.insertBook(bookedTour)

            user!!.account_money = user.account_money - _totalFee.value!!
            dao.updateUser(user)
            _payResult.value = PayResult.Valid
        }
    }

    fun getTourName(tourId: Int): String {
        val tour = dao.getTourByTourId(tourId)
        return tour!!.tour_name
    }

    fun isRadioButtonChecked(
        radioButton: RadioButton,
        radioButton2: RadioButton,
        radioButton3: RadioButton
    ): Boolean {
        return (radioButton.isChecked || radioButton2.isChecked || radioButton3.isChecked)
    }

    fun isBreakfastChecked(chip: Chip): Boolean {
        return chip.isChecked
    }


}