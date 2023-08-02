package com.berkpolat.termproject.book

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.berkpolat.termproject.R
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentBookTourBinding


class BookTourFragment : Fragment() {
    private var _binding: FragmentBookTourBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookTourBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = BookTourViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(BookTourViewModel::class.java)

        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = binding.bottomNav
        bottomNavView.setupWithNavController(navController)

        val user = BookTourFragmentArgs.fromBundle(requireArguments()).userName
        val tourId = BookTourFragmentArgs.fromBundle(requireArguments()).tourId
        val money = BookTourFragmentArgs.fromBundle(requireArguments()).money


        val check = binding.agreementBox

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val breakfastChip = binding.chip

        binding.nameTxt.text = viewModel.getTourName(tourId)



        binding.payBtn.setOnClickListener {

            viewModel.pay(
                user,
                check,
                tourId,
                binding.radioButtonDefault,
                binding.radioButtonPers1,
                binding.radioButtonPers2
            )


        }


        binding.radioButtonDefault.setOnClickListener {
            viewModel.getTotalFee(tourId, binding.noExtraTxt)
        }

        binding.radioButtonPers1.setOnClickListener {

            viewModel.getTotalFee(tourId, binding.extra1)
        }

        binding.radioButtonPers2.setOnClickListener {

            viewModel.getTotalFee(tourId, binding.extra2)
        }


        viewModel.payResult.observe(viewLifecycleOwner, Observer { pay ->
            when (pay) {
                is BookTourViewModel.PayResult.Valid -> {
                    if (viewModel.isBreakfastChecked(breakfastChip)) {
                        Toast.makeText(context, R.string.breakfast_msg, Toast.LENGTH_SHORT).show()
                    }
                    val action =
                        BookTourFragmentDirections.actionBookTourFragmentToBookedTourDetailsFragment(
                            user,
                            (money - binding.feeTxt.text.toString().toFloat())
                        )
                    findNavController().navigate(action)
                }
                is BookTourViewModel.PayResult.NotEnoughMoney -> {
                    Toast.makeText(context, R.string.no_money_msg, Toast.LENGTH_SHORT).show()
                }
                is BookTourViewModel.PayResult.EmptyField -> {

                    Toast.makeText(context, R.string.agree_msg, Toast.LENGTH_SHORT).show()
                }
                is BookTourViewModel.PayResult.RadioNotSelected -> {
                    Toast.makeText(context, R.string.radio_msg, Toast.LENGTH_SHORT).show()
                }
            }

        })

        viewModel.totalFee.observe(viewLifecycleOwner, Observer { fee ->
            binding.feeTxt.text = fee.toString()
        })




        bottomNavView.setOnItemSelectedListener { item ->
            onOptionsItemSelected(item)
        }

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val user = BookTourFragmentArgs.fromBundle(requireArguments()).userName
        val money = BookTourFragmentArgs.fromBundle(requireArguments()).money

        when (item.itemId) {
            R.id.homeFragment -> {
                val action = BookTourFragmentDirections.actionBookTourFragmentToHomeFragment(
                    user,
                    money
                )
                findNavController().navigate(action)

            }
            R.id.myWalletFragment -> {
                val action = BookTourFragmentDirections.actionBookTourFragmentToMyWalletFragment(
                    money, user
                )
                findNavController().navigate(action)
            }
            else -> {
                return false
            }
        }
        return true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

