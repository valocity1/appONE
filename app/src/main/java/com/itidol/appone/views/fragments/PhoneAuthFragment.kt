package com.itidol.appone.views.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.itidol.appone.R
import com.itidol.appone.databinding.FragmentPhoneAuthBinding
import com.itidol.appone.views.activities.MainActivity
import java.util.concurrent.TimeUnit

class PhoneAuthFragment : Fragment() {
    lateinit var binding: FragmentPhoneAuthBinding
    lateinit var firebaseAuth: FirebaseAuth
    var number: String? = null
    lateinit var customView: View
    lateinit var alertDialog: AlertDialog


    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneAuthBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        customView = layoutInflater.inflate(R.layout.alert_dialog, null)
        alertDialog = AlertDialog.Builder(requireActivity()).setView(customView).create()
        // Inflate the layout for this fragment
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                (activity as MainActivity).getFragments(BlankFragment())
                Toast.makeText(requireActivity(), "Verification Completed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(requireActivity(), "" + p0.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token

            }
        }

        binding.loginButton.setOnClickListener {
            login()

            alertDialog.create()
            alertDialog.show()
        }

        customView.findViewById<Button>(R.id.verifyOTP).setOnClickListener {
            val otp = customView.findViewById<EditText>(R.id.editText)
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                storedVerificationId.toString(), otp.text.toString()
            )
            signInWithPhoneAuthCredential(credential)
        }
        return binding.root
    }

    // login function
    private fun login() {
        number = binding.editTextPhoneNumber.text.trim().toString()
        if (number!!.isNotEmpty()) {
            sendVerificationCode("+91" + number!!)
        } else {
            Toast.makeText(requireActivity(), "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Toast.makeText(requireActivity(), "Auth Started", Toast.LENGTH_SHORT).show()
    }

    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    (activity as MainActivity).getFragments(BlankFragment())
                    alertDialog.dismiss()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(requireActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}