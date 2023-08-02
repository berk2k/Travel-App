package com.berkpolat.termproject.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.berkpolat.termproject.R
import com.berkpolat.termproject.databases.TourAppDatabase
import com.berkpolat.termproject.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao

        val viewModelFactory = RegisterViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        val toolbar = binding.registerToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.title = view.resources.getString(R.string.register_title)

        binding.registerBtn.setOnClickListener {
            val userName = binding.userNameTextView.text.toString()
            val email = binding.emailTextView.text.toString()
            val password = binding.passwordTextView.text.toString()
            val password2 = binding.passwordTextView2.text.toString()

            viewModel.register(userName, email, password, password2)
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->

            when (result) {
                is RegisterViewModel.RegisterResult.Valid -> {
                    Toast.makeText(context, R.string.acc_created_msg, Toast.LENGTH_SHORT)
                        .show()
                    val action = RegisterFragmentDirections
                        .actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
                is RegisterViewModel.RegisterResult.InvalidEmail -> {
                    binding.emailTextView.error = "Invalid email"
                }
                is RegisterViewModel.RegisterResult.EmptyFields -> {
                    Toast.makeText(
                        context,
                        R.string.empty_msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is RegisterViewModel.RegisterResult.PasswordNotMatch -> {
                    Toast.makeText(
                        context,
                        R.string.invalid_pass_msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is RegisterViewModel.RegisterResult.UserNameTaken -> {
                    Toast.makeText(
                        context,
                        R.string.invalid_user_msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is RegisterViewModel.RegisterResult.EmailTaken -> {
                    Toast.makeText(
                        context,
                        R.string.invalid_mail_msg,
                        Toast.LENGTH_SHORT
                    ).show()
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