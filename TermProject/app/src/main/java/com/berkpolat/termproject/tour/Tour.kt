package com.berkpolat.termproject.tour

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tours")
data class Tour(
    @PrimaryKey
    var tourId: Int,
    @ColumnInfo(name = "tour_name")
    var tour_name: String = "",
    @ColumnInfo(name = "destination")
    var destination: String = "",
    @ColumnInfo(name = "cost")
    var tourCost: Float = 0f,
    @ColumnInfo(name = "hotel")
    var hotelName: String = "",
    @ColumnInfo(name = "date")
    var date: String = "",
    @ColumnInfo(name = "image")
    var image: Int
)
