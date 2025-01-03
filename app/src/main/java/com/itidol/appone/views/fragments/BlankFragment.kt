package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    lateinit var binding: FragmentBlankBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlankBinding.inflate(layoutInflater)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }

        })
        firebaseAuth = FirebaseAuth.getInstance()
        binding.logoutImage.setOnClickListener {
            firebaseAuth.signOut()
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

}