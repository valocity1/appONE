package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentRegisterBinding
import com.itidol.appone.views.activities.MainActivity

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.registerButtonLoginFragment.setOnClickListener {
            (activity as MainActivity).getFragments(BlankFragment())
        }

        binding.loginNowTxtloginFragment.setOnClickListener {
            (activity as MainActivity).getFragments(LoginFragment())
        }

        binding.registerImageViewBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }
}