package com.berkpolat.termproject.tour

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.berkpolat.termproject.R
import com.berkpolat.termproject.interfaces.AppDao

class TourViewModel(val dao: AppDao) : ViewModel() {

    fun getAccMoney(userName: String): Float {
        val money = dao.getAccMoneyByUserName(userName)
        return money!!
    }

    fun getTour(tourId: Int): Tour {
        val tour = dao.getTourByTourId(tourId)
        return tour!!
    }

    fun getImage(tourId: Int): Int {
        val tour = getTour(tourId)
        return tour.image
    }

    fun getName(tourId: Int): String {
        val tour = getTour(tourId)
        return tour.tour_name
    }

    fun getDestination(tourId: Int): String {
        val tour = getTour(tourId)
        return tour.destination
    }


    fun getHotel(tourId: Int): String {
        val tour = getTour(tourId)
        return tour.hotelName
    }

    fun getDate(tourId: Int): String {
        val tour = getTour(tourId)
        return tour.date
    }

    fun getCost(tourId: Int): Float {
        val tour = getTour(tourId)
        return tour.tourCost
    }


    fun setAndGetDescription(tourId: Int, textView: TextView, view: View): String {

        when (tourId) {
            1 -> {
                textView.text = view.resources.getString(R.string.ny_desc)

            }
            2 -> {
                textView.text = view.resources.getString(R.string.ist_desc)
            }
            3 -> {
                textView.text = view.resources.getString(R.string.jpn_desc)
            }
            4 -> {
                textView.text = view.resources.getString(R.string.ams_desc)
            }
            5 -> {
                textView.text = view.resources.getString(R.string.rome_desc)
            }

        }
        return textView.text.toString()
    }
}