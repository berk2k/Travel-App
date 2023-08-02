package com.berkpolat.termproject.tour

import androidx.recyclerview.widget.DiffUtil

class TourDiffItemCallback : DiffUtil.ItemCallback<Tour>() {
    override fun areItemsTheSame(oldItem: Tour, newItem: Tour) = (oldItem.tourId == newItem.tourId)


    override fun areContentsTheSame(oldItem: Tour, newItem: Tour) = (oldItem == newItem)


}