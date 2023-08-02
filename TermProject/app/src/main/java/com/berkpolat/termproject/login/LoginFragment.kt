package com.berkpolat.termproject.login

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
import com.berkpolat.termproject.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TourAppDatabase.getInstance(application).appDao

        val viewModelFactory = LoginViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        val toolbar = binding.loginToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //about page
        toolbar.setNavigationOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToAboutFragment()
            findNavController().navigate(action)
        }



        binding.loginButton.setOnClickListener {
            val email = binding.emailTextView.text.toString()
            val password = binding.passwordTextView.text.toString()

            viewModel.login(email, password)

        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections
                .actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }


        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginViewModel.LoginResult.Valid -> {
                    val action = LoginFragmentDirections
                        .actionLoginFragmentToHomeFragment(
                            viewModel.user.user_name,
                            viewModel.user.account_money
                        )
                    findNavController().navigate(action)
                }
                is LoginViewModel.LoginResult.InvalidEmail -> {
                    binding.emailTextView.error = "Invalid email"
                }
                is LoginViewModel.LoginResult.EmptyFields -> {
                    Toast.makeText(
                        context,
                        R.string.empty_fields_msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is LoginViewModel.LoginResult.UserNotFound -> {
                    Toast.makeText(context, R.string.user_not_found_msg, Toast.LENGTH_SHORT).show()
                }
                is LoginViewModel.LoginResult.IncorrectPassword -> {
                    binding.passwordTextView.error = "Invalid password"
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