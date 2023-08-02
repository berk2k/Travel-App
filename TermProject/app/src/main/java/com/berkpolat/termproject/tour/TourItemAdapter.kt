package com.berkpolat.termproject.tour


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.berkpolat.termproject.databinding.TourItemBinding

class TourItemAdapter(val clickListener: (tourId: Int) -> Unit) :
    ListAdapter<Tour, TourItemAdapter.TourItemViewHolder>(TourDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourItemViewHolder =
        TourItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TourItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TourItemViewHolder(val binding: TourItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var image = binding.tourImage

        companion object {
            fun inflateFrom(parent: ViewGroup): TourItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = TourItemBinding.inflate(layoutInflater, parent, false)
                return TourItemViewHolder(binding)
            }

        }


        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Tour, clickListener: (tourId: Int) -> Unit) {
            binding.tour = item
        //    image.setImageDrawable(binding.root.context.getDrawable(item.image))
            image.setBackgroundResource(item.image)

            binding.root.setOnClickListener {
                clickListener(item.tourId)
            }
        }
    }


}