package com.berkpolat.termproject.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berkpolat.termproject.cards.CreditCard
import com.berkpolat.termproject.tour.Tour
import com.berkpolat.termproject.User
import com.berkpolat.termproject.bookDetails.Book
import com.berkpolat.termproject.interfaces.AppDao

@Database(entities = [User::class, CreditCard::class, Tour::class, Book::class], version = 1)
abstract class TourAppDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    companion object {
        @Volatile
        private var INSTANCE: TourAppDatabase? = null

        fun getInstance(context: Context): TourAppDatabase {

            val tmpInstance = INSTANCE
            if (tmpInstance != null) {
                return tmpInstance
            }
            synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TourAppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}