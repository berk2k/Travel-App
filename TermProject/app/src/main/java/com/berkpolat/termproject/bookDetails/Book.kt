package com.berkpolat.termproject.bookDetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booked")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var bookId: Int = 0,
    @ColumnInfo(name = "tourId")
    var tourId: Int = 0,
    @ColumnInfo(name = "cost")
    var cost: Float = 0f,
    @ColumnInfo(name = "userName")
    var userName: String = "",
    @ColumnInfo(name = "destination")
    var destination: String = "",
    @ColumnInfo(name = "hotel")
    var hotelName: String = "",
    @ColumnInfo(name = "date")
    var date: String = ""
)
