package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentLoginBinding
import com.itidol.appone.views.activities.MainActivity
import com.itidol.appone.utils.Java_Regex

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.registerNowTxtloginFragment.setOnClickListener {
            (activity as MainActivity).getFragments(RegisterFragment())
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
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
//                    requireActivity().finish()
                }
            })
        binding.loginButtonLoginFragment.setOnClickListener {
            val email = binding.userEmailLoginFragment.text.toString()
            val password = binding.passwordLoginFragment.text.toString()

            if (!Java_Regex.emailValidator(email)) {
                binding.userEmailLoginFragment.setError("Email Doesn't Match Requirement");
            } else if (!Java_Regex.passwordValidator(password)) {
                binding.passwordLoginFragment.setError(
                    "Password must:\n" +
                            "- Be at least 8 characters long\n" +
                            "- Contain at least one uppercase letter\n" +
                            "- Contain at least one lowercase letter\n" +
                            "- Contain at least one digit\n" +
                            "- Contain at least one special character (@$!%*?&)"
                )
            } else {
                binding.userEmailLoginFragment.error = null
                binding.passwordLoginFragment.error = null
                loginWithCredentials(email, password)
            }
        }

        binding.facebookUserLoginFragment.setOnClickListener{
            (activity as MainActivity).getFragments(PhoneAuthFragment())
        }



        return binding.root
    }

    fun loginWithCredentials(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(), object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        Toast.makeText(requireActivity(), "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        (activity as MainActivity).getFragments(BlankFragment())
                    }
                }

            }).addOnFailureListener {
                Toast.makeText(requireActivity(), "" + it.message, Toast.LENGTH_SHORT).show()
            }
    }

}