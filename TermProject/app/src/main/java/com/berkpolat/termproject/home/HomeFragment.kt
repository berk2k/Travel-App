package com.berkpolat.termproject.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.berkpolat.termproject.R
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentHomeBinding
import com.berkpolat.termproject.tour.TourItemAdapter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao
        val viewModelFactory = HomeViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val navView = binding.navView
        val toolbar = binding.homeToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val drawer = binding.drawerLayout
        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navView, navController)

        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawer)
     //   val appBarConfiguration = builder.build()
        //    toolbar.setupWithNavController(navController, appBarConfiguration)




        val adapter = TourItemAdapter { tourId ->
            viewModel.onTourClicked(tourId)
        }
        binding.toursList.adapter = adapter

        val user = HomeFragmentArgs.fromBundle(requireArguments()).userName
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        toolbar.setNavigationOnClickListener {
            drawer.open()
        }


        binding.searchBtn.setOnClickListener {
            val destination = binding.searchCity.text.toString()
            viewModel.searchCity(destination)
        }


        viewModel.tours.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToTourDetail.observe(viewLifecycleOwner, Observer { tourId ->
            tourId?.let {

                val action =
                    HomeFragmentDirections.actionHomeFragmentToTourDetailsFragment(tourId, user)
                this.findNavController().navigate(action)
                viewModel.onTourNavigated()
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HomeViewModel.SearchResult.Found -> {

                    val action =
                        HomeFragmentDirections.actionHomeFragmentToTourDetailsFragment(
                            viewModel.tour.tourId,
                            user
                        )
                    this.findNavController().navigate(action)
                }
                is HomeViewModel.SearchResult.NotFound -> {
                    binding.searchCity.error = "City Not Found."
                }
                is HomeViewModel.SearchResult.EmptyField -> {
                    binding.searchCity.error = "Empty Field"
                }
            }
        }


        //navigation drawer
        navView.setNavigationItemSelectedListener { item ->
            onOptionsItemSelected(item)
        }



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).menuInflater.inflate(R.menu.home_menu, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val money = HomeFragmentArgs.fromBundle(requireArguments()).accountMoney
        val user = HomeFragmentArgs.fromBundle(requireArguments()).userName
        when (item.itemId) {
            R.id.myWalletFragment -> {

                val action =
                    HomeFragmentDirections.actionHomeFragmentToMyWalletFragment(money, user)
                findNavController().navigate(action)
            }
            R.id.bookedTourDetailsFragment -> {
                val action = HomeFragmentDirections.actionHomeFragmentToBookedTourDetailsFragment(
                    user,
                    money
                )
                findNavController().navigate(action)
            }
            R.id.loginFragment -> {
                val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
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





