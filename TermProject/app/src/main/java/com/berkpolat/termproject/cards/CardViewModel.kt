package com.berkpolat.termproject.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.interfaces.AppDao
import kotlinx.coroutines.launch

class CardViewModel(private val dao: AppDao) : ViewModel() {
    lateinit var card: CreditCard

    sealed class CardResult {
        object Valid : CardResult()
        object InvalidCardNo : CardResult()
        object EmptyField : CardResult()
        object InvalidDate : CardResult()
        object InvalidCVV : CardResult()
    }

    private var _cardResult = MutableLiveData<CardResult>()
    val cardResult: LiveData<CardResult>
        get() = _cardResult

    fun addCard(name: String, cardNo: String, expDate: Int, cvv: Int) {
        if (name.isBlank() || cardNo.isBlank() || expDate.toString().isBlank() || cvv.toString()
                .isBlank()
        ) {
            _cardResult.value = CardResult.EmptyField
            return
        }
        if (!isDateValid(expDate)) {
            _cardResult.value = CardResult.InvalidDate
            return
        }
        if (!isCardNoValid(cardNo)) {
            _cardResult.value = CardResult.InvalidCardNo
            return
        }
        if (!isCvvValid(cvv)) {
            _cardResult.value = CardResult.InvalidCVV
            return
        }

        viewModelScope.launch {
            card = CreditCard()
            card.name = name
            card.card_no = cardNo
            card.exp_date = expDate
            card.cvv = cvv

            dao.insertCard(card)
            _cardResult.value = CardResult.Valid


        }
    }

    //Card acceptable if expire date between 2023-2033
    private fun isDateValid(date: Int): Boolean {
        return !(date < 2023 || date > 2033)
    }

    //card number length must be 12
    private fun isCardNoValid(cardNo: String): Boolean {
        if (cardNo.length != 12) {
            return false
        }
        return true
    }

    //cvv length must be 3
    private fun isCvvValid(cvv: Int): Boolean {
        if (cvv.toString().length != 3) {
            return false
        }
        return true
    }


}