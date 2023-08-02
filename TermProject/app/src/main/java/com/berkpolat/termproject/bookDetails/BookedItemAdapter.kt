package com.berkpolat.termproject.bookDetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.berkpolat.termproject.databinding.BookedItemBinding


class BookedItemAdapter(
    val clickListener: (bookId: Int) -> Unit
) :
    ListAdapter<Book, BookedItemAdapter.BookedItemViewHolder>(BookDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedItemViewHolder =
        BookedItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BookedItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class BookedItemViewHolder(val binding: BookedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun inflateFrom(parent: ViewGroup): BookedItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = BookedItemBinding.inflate(layoutInflater, parent, false)
                return BookedItemViewHolder(binding)
            }

        }


        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Book, clickListener: (bookId: Int) -> Unit) {
            binding.book = item

            binding.deleteBtn.setOnClickListener {
                clickListener(item.bookId)
            }
        }
    }
}