package com.berkpolat.termproject.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.berkpolat.termproject.R
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentMyWalletBinding


class MyWalletFragment : Fragment() {
    private var _binding: FragmentMyWalletBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyWalletBinding.inflate(inflater, container, false)
        val view = binding.root
        val money = MyWalletFragmentArgs.fromBundle(requireArguments()).accountMoney
        val user = MyWalletFragmentArgs.fromBundle(requireArguments()).userName

        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = MyWalletViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MyWalletViewModel::class.java)

        binding.user = viewModel.getUser(user)
        binding.lifecycleOwner = viewLifecycleOwner

        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = binding.bottomNavMenu
        bottomNavView.setupWithNavController(navController)

        //    binding.moneyText.text = money.toString()

        binding.addMoneyBtn.setOnClickListener {
            val action = MyWalletFragmentDirections
                .actionMyWalletFragmentToAddMoneyFragment(user)
            findNavController().navigate(action)
        }

        bottomNavView.setOnItemSelectedListener { item ->
            onOptionsItemSelected(item)
        }



        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val user = MyWalletFragmentArgs.fromBundle(requireArguments()).userName
        val money = MyWalletFragmentArgs.fromBundle(requireArguments()).accountMoney

        when (item.itemId) {
            R.id.homeFragment -> {
                val action = MyWalletFragmentDirections.actionMyWalletFragmentToHomeFragment(
                    user,
                    money
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