package com.berkpolat.termproject.cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "credit_cards")
data class CreditCard(
    @PrimaryKey
    var card_no: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "expiry_date")
    var exp_date: Int = 0,
    @ColumnInfo(name = "cvv")
    var cvv: Int = 0,
    @ColumnInfo(name = "money")
    var money: Float = 10000F

)
