package com.berkpolat.termproject.addmoney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.cards.CreditCard
import com.berkpolat.termproject.User
import com.berkpolat.termproject.interfaces.AppDao
import kotlinx.coroutines.launch

class AddMoneyViewModel(private val dao: AppDao) : ViewModel() {
    lateinit var card: CreditCard
    lateinit var user: User



    sealed class AddMoneyResult {
        object Valid : AddMoneyResult()
        object EmptyField : AddMoneyResult()
        object InvalidCardNo : AddMoneyResult()
        object InvalidCardDate : AddMoneyResult()
        object InvalidCvv : AddMoneyResult()
        object InvalidMoneyAmount : AddMoneyResult()
    }

    private var _moneyResult = MutableLiveData<AddMoneyResult>()
    val moneyResult: LiveData<AddMoneyResult>
        get() = _moneyResult

    fun addMoney(userName: String, cardNo: String, expDate: Int, cvv: Int, money: Float) {
        if (cardNo.isBlank() || expDate.toString().isBlank() || cvv.toString()
                .isBlank() || money.toString().isBlank()
        ) {
            _moneyResult.value = AddMoneyResult.EmptyField
            return
        }
        if (!isCardExists(cardNo)) {
            _moneyResult.value = AddMoneyResult.InvalidCardNo
            return
        }
        if (!isDateValid(cardNo, expDate)) {
            _moneyResult.value = AddMoneyResult.InvalidCardDate
            return
        }
        if (!isCvvValid(cardNo, cvv)) {
            _moneyResult.value = AddMoneyResult.InvalidCvv
            return
        }
        if (!isMoneyAmountValid(cardNo, money)) {
            _moneyResult.value = AddMoneyResult.InvalidMoneyAmount
            return
        }

        viewModelScope.launch {
            card = dao.getCardByCardNo(cardNo)!!
            //update money amount in used credit card
            card.money -= money
            dao.updateCard(card)

            //update users account money
            user = dao.getUserByUserName(userName)!!
            user.account_money += money
            dao.updateUser(user)

            _moneyResult.value = AddMoneyResult.Valid

        }
    }

    private fun isCardExists(cardNo: String): Boolean {
        val cardNumbers = dao.getAllCardNumbers()

        for (i in cardNumbers.indices) {
            if (cardNo == cardNumbers[i]) {
                return true
            }
        }
        return false
    }

    private fun isDateValid(cardNo: String, date: Int): Boolean {
        val cardDate = dao.getDateByCardNo(cardNo)
        return date == cardDate

    }

    private fun isCvvValid(cardNo: String, cvv: Int): Boolean {
        val cardCvv = dao.getCvvByCardNo(cardNo)
        return cvv == cardCvv
    }

    //1-you can't add money if you trying to add more money than your card has
    //2-You should at least add 10 dollars
    private fun isMoneyAmountValid(cardNo: String, money: Float): Boolean {
        val currMoney = dao.getMoneyByCardNo(cardNo)
        return !(money > currMoney!! || money < 10)

    }

    fun getMoney(userName: String):Float{
        val userMoney = dao.getAccMoneyByUserName(userName)
        return userMoney!!
    }
}