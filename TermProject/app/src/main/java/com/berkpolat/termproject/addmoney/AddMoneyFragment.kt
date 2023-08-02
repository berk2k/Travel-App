package com.berkpolat.termproject.addmoney

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

import com.berkpolat.termproject.databinding.FragmentAddMoneyBinding


class AddMoneyFragment : Fragment() {
    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMoneyBinding.inflate(inflater, container, false)
        val view = binding.root
        val user = AddMoneyFragmentArgs.fromBundle(requireArguments()).userName

        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao

        val viewModelFactory = AddMoneyViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(AddMoneyViewModel::class.java)

        val toolbar = binding.addMoneyToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            val action = AddMoneyFragmentDirections.actionAddMoneyFragmentToMyWalletFragment(
                viewModel.getMoney(user),
                user
            )
            findNavController().navigate(action)
        }

        binding.cardBtn.setOnClickListener {
            val action = AddMoneyFragmentDirections
                .actionAddMoneyFragmentToCardFragment(user)
            findNavController().navigate(action)
        }

        binding.moneyBtn.setOnClickListener {
            if (binding.enterMoneyTxt.text.isBlank() || binding.enterCvvTxt.text.isBlank() || binding.enterDateTxt.text.isBlank()) {
                Toast.makeText(
                    context,
                    R.string.empty_msg,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val cardNo = binding.enterCardTxt.text.toString()
                val date = binding.enterDateTxt.text.toString()
                val cvv = binding.enterCvvTxt.text.toString()
                val money = binding.enterMoneyTxt.text.toString()


                viewModel.addMoney(
                    user,
                    cardNo,
                    date.toInt(),
                    cvv.toInt(),
                    money.toFloat(),
                )
            }


        }

        viewModel.moneyResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AddMoneyViewModel.AddMoneyResult.Valid -> {
                    Toast.makeText(context, R.string.money_added_msg, Toast.LENGTH_SHORT)
                        .show()
                    val action =
                        AddMoneyFragmentDirections.actionAddMoneyFragmentToMyWalletFragment(
                            viewModel.user.account_money, user    //send updated money
                        )
                    findNavController().navigate(action)
                }
                is AddMoneyViewModel.AddMoneyResult.EmptyField -> {
                    Toast.makeText(
                        context,
                        R.string.empty_msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is AddMoneyViewModel.AddMoneyResult.InvalidCardNo -> {
                    binding.enterCardTxt.error = view.resources.getString(R.string.invalid_card_msg)
                }
                is AddMoneyViewModel.AddMoneyResult.InvalidCardDate -> {
                    binding.enterDateTxt.error = view.resources.getString(R.string.invalid_card_date_msg)
                }
                is AddMoneyViewModel.AddMoneyResult.InvalidCvv -> {
                    binding.enterCvvTxt.error = view.resources.getString(R.string.invalid_card_cvv_msg)
                }
                is AddMoneyViewModel.AddMoneyResult.InvalidMoneyAmount -> {
                    binding.enterMoneyTxt.error =
                        view.resources.getString(R.string.invalid_money_amount)
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