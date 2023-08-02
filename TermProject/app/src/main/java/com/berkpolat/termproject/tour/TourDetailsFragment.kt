package com.berkpolat.termproject.tour

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentTourDetailsBinding


class TourDetailsFragment : Fragment() {
    private var _binding: FragmentTourDetailsBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTourDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = TourViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)

        val user = TourDetailsFragmentArgs.fromBundle(requireArguments()).userName
        val tourId = TourDetailsFragmentArgs.fromBundle(requireArguments()).tourId
        val money = viewModel.getAccMoney(user)

        val img = viewModel.getImage(tourId)
        val desc = viewModel.setAndGetDescription(tourId,binding.descriptionText,view)


        binding.tour = viewModel.getTour(tourId)

        binding.expandImage.setImageDrawable(context?.getDrawable(img))
        binding.descriptionText.text = desc

        binding.bookBtn.setOnClickListener {
            val action = TourDetailsFragmentDirections.actionTourDetailsFragmentToBookTourFragment(
                user,
                tourId,
                money
            )
            this.findNavController().navigate(action)
        }

        binding.faBtn.setOnClickListener {
            val action =
                TourDetailsFragmentDirections.actionTourDetailsFragmentToHomeFragment(user, money)
            this.findNavController().navigate(action)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}