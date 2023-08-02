package com.berkpolat.termproject.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkpolat.termproject.R
import com.berkpolat.termproject.interfaces.AppDao
import com.berkpolat.termproject.tour.Tour
import kotlinx.coroutines.launch

class TourServerViewModel(val dao: AppDao) : ViewModel() {


    val tour1 = Tour(
        1,
        "NY tour",
        "New York",
        500f,
        "The Peninsula",
        "10 06 23 - 14 06 23",
        R.drawable.ny
    )
    val tour2 = Tour(
        2,
        "IST tour",
        "Istanbul",
        300f,
        "Mandarin",
        "27 05 23 - 1 06 23",
        R.drawable.istanbul
    )
    val tour3 = Tour(
        3,
        "JPN tour",
        "Japan",
        400f,
        "The Prince Kyoto",
        "1 07 23 - 6 07 23",
        R.drawable.japan
    )
    val tour4 = Tour(
        4,
        "AMS tour",
        "Amsterdam",
        450f,
        "Doubletree",
        "12 08 23 - 16 08 23",
        R.drawable.amsterdam
    )
    val tour5 = Tour(
        5,
        "ROME tour",
        "Rome",
        350f,
        "Giolli Nazionale",
        "3 09 23 - 6 09 23",
        R.drawable.rome
    )


    fun insertTour() {
        viewModelScope.launch {
            dao.insertTour(tour1)
            dao.insertTour(tour2)
            dao.insertTour(tour3)
            dao.insertTour(tour4)
            dao.insertTour(tour5)
        }
    }
}