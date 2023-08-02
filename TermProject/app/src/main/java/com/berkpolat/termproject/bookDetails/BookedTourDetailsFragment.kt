package com.berkpolat.termproject.bookDetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.berkpolat.termproject.R

import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentBookedTourDetailsBinding

import com.google.android.material.snackbar.Snackbar


class BookedTourDetailsFragment : Fragment() {
    private var _binding: FragmentBookedTourDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookedTourDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = BookedTourViewModelFactory(dao)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(BookedTourViewModel::class.java)

        val toolbar = binding.bookedToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val user = BookedTourDetailsFragmentArgs.fromBundle(requireArguments()).user
        val money = BookedTourDetailsFragmentArgs.fromBundle(requireArguments()).money

        val adapter = BookedItemAdapter { bookId ->
            Snackbar.make(view, R.string.tour_deleted, Snackbar.LENGTH_SHORT)
                .setAction(R.string.approve_msg) {
                    viewModel.onDeleteClicked(bookId, user)
                }.show()

        }

        binding.bookDetailsList.adapter = adapter




        viewModel.saveTour(user)
        viewModel.bookedTours.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.deleteTour.observe(viewLifecycleOwner, Observer { bookId ->
            bookId?.let {

                Toast.makeText(context, R.string.tour_deleted_msg, Toast.LENGTH_SHORT)
                    .show()
                viewModel.onTourDeleted()
            }
        })

        toolbar.setNavigationOnClickListener {
            if (viewModel.returnMoney != 0f) {
                val action =
                    BookedTourDetailsFragmentDirections.actionBookedTourDetailsFragmentToHomeFragment(
                        user,
                        (viewModel.user.account_money)
                    )
                findNavController().navigate(action)
            } else {
                val action =
                    BookedTourDetailsFragmentDirections.actionBookedTourDetailsFragmentToHomeFragment(
                        user,
                        (money)
                    )
                findNavController().navigate(action)
            }

        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}