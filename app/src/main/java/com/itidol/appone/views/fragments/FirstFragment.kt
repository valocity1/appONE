package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentFirstBinding
import com.itidol.appone.views.activities.MainActivity

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.loginButtonTextView.setOnClickListener {
            (activity as MainActivity).getFragments(LoginFragment())
        }
        binding.RegisterButtonTextView.setOnClickListener {
            (activity as MainActivity).getFragments(RegisterFragment())
        }
//        binding.continueGuestText.setOnClickListener {
//            (activity as MainActivity).getFragments(BlankFragment())
//        }


        return binding.root
    }

}