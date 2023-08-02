package com.berkpolat.termproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.server.TourServerViewModel
import com.berkpolat.termproject.server.TourServerViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val application = requireNotNull(this).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = TourServerViewModelFactory(dao)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(TourServerViewModel::class.java)

        val tourSize = dao.getTourSize()


        if (tourSize == 0) {
            viewModel.insertTour()
        } else {
            println("Tours already added.")
        }

    }
}