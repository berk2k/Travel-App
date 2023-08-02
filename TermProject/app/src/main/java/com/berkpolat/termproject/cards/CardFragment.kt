package com.berkpolat.termproject.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.berkpolat.termproject.R
import com.berkpolat.termproject.databases.TourAppDatabase

import com.berkpolat.termproject.databinding.FragmentCardBinding


class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val view = binding.root
        val userName = CardFragmentArgs.fromBundle(requireArguments()).userName

        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao

        val viewModelFactory = CardViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CardViewModel::class.java)

        val toolbar = binding.addMoneyToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            val action = CardFragmentDirections.actionCardFragmentToAddMoneyFragment(userName)
            findNavController().navigate(action)
        }


        binding.addBtn.setOnClickListener {
            if (binding.cvvText.text.isBlank() || binding.dateText.text.isBlank()) {
                Toast.makeText(
                    context,
                    R.string.empty_msg,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = binding.nameText.text.toString()
                val cardNo = binding.cardNoText.text.toString()
                val date = binding.dateText.text.toString()
                val cvv = binding.cvvText.text.toString()

                viewModel.addCard(name, cardNo, date.toInt(), cvv.toInt())
            }

        }

        viewModel.cardResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is CardViewModel.CardResult.Valid -> {
                    Toast.makeText(context, R.string.card_added_msg, Toast.LENGTH_SHORT)
                        .show()
                    val action = CardFragmentDirections
                        .actionCardFragmentToAddMoneyFragment(userName)
                    findNavController().navigate(action)
                }
                is CardViewModel.CardResult.InvalidCardNo -> {

                    binding.cardNoText.error = view.resources.getString(R.string.invalid_card_msg2)
                }
                is CardViewModel.CardResult.InvalidCVV -> {
                    binding.cvvText.error = view.resources.getString(R.string.invalid_card_cvv_msg2)
                }
                is CardViewModel.CardResult.InvalidDate -> {
                    binding.dateText.error = view.resources.getString(R.string.invalid_card_date_msg2)
                }
                is CardViewModel.CardResult.EmptyField -> {
                    Toast.makeText(context, R.string.empty_msg, Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}