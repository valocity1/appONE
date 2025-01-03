package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentLoginBinding
import com.itidol.appone.views.activities.MainActivity

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.registerNowTxtloginFragment.setOnClickListener {
            (activity as MainActivity).getFragments(RegisterFragment())
        }
        binding.loginButtonLoginFragment.setOnClickListener {
            (activity as MainActivity).getFragments(BlankFragment())
        }

        binding.imageViewBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.forgotPasswordLoginScreen.setOnClickListener {
            (activity as MainActivity).getFragments(BlankFragment())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    requireActivity().finish()
                }
            })
        return binding.root
    }


}