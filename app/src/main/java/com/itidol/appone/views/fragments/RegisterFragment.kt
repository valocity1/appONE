package com.itidol.appone.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentRegisterBinding
import com.itidol.appone.views.activities.MainActivity
import com.itidol.appone.preferences.UserSharedPreferences
import com.itidol.appone.utils.Java_Regex

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth
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
        firebaseAuth = FirebaseAuth.getInstance()
        //Register Button
        binding.registerButtonLoginFragment.setOnClickListener {
            val username = binding.userNameRegisterFragment.text.toString()
            val email = binding.emailRegisterFragment.text.toString()
            val password = binding.passwordRegisterFragment.text.toString()
            val confirmPassword = binding.confirmPasswordRegisterFragment.text.toString()

            if (username.isEmpty()) {
                binding.userNameRegisterFragment.error = "Username is required"
            }   else if (!Java_Regex.emailValidator(email)) {
            binding.emailRegisterFragment.setError("Email Doesn't Match Requirement");
        } else if (!Java_Regex.passwordValidator(password)) {
            binding.passwordRegisterFragment.setError(
                "Password must:\n" +
                        "- Be at least 8 characters long\n" +
                        "- Contain at least one uppercase letter\n" +
                        "- Contain at least one lowercase letter\n" +
                        "- Contain at least one digit\n" +
                        "- Contain at least one special character (@$!%*?&)"
            )
            } else if (password != confirmPassword) {
                binding.confirmPasswordRegisterFragment.error = "Password does not match"
            } else {
                binding.userNameRegisterFragment.error = null
                binding.emailRegisterFragment.error = null
                binding.passwordRegisterFragment.error = null
                binding.confirmPasswordRegisterFragment.error = null
                //save user credentials to shared preferences
                val userSharedPreferences = UserSharedPreferences(requireActivity())
                userSharedPreferences.setUserLoginCredentials(username, email, confirmPassword)
                //save user credentials to firebase
                createUser(email, confirmPassword)
            }
        }
        binding.facebookUserRegisterFragment.setOnClickListener{
            (activity as MainActivity).getFragments(PhoneAuthFragment())
        }

        return binding.root
    }

    fun createUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(), object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        firebaseUser!!.sendEmailVerification().addOnSuccessListener {
                            Toast.makeText(
                                requireActivity(),
                                "Email Verification link sent on Registered email, Please Verify Before Login! ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireActivity(),
                                "" + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        (activity as MainActivity).getFragments(BlankFragment())
                    }
                }
            })
    }
}