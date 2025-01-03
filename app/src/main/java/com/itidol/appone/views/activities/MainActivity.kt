package com.itidol.appone.views.activities

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.itidol.appone.R
import com.itidol.appone.databinding.ActivityMainBinding
import com.itidol.appone.views.fragments.BlankFragment
import com.itidol.appone.views.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(savedInstanceState!=null){
         getFragments(FirstFragment())
        }
        //Attach First Fragment
        val attachedFragment = supportFragmentManager.beginTransaction()
        attachedFragment.replace(binding.framelayout.id, FirstFragment()).commit()
        //firebase Auth
        val firebaseAuth = FirebaseAuth.getInstance()
        //Check if user is logged in
        if(firebaseAuth.currentUser != null){
            getFragments(BlankFragment())
        }else{
            getFragments(FirstFragment())
        }
    }

    //Function to replace fragments
    fun getFragments(fragments: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(binding.framelayout.id, fragments)
        fragment.addToBackStack(null)
        fragment.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val currentFragment = supportFragmentManager.findFragmentById(binding.framelayout.id)
        if(currentFragment!=null){
            supportFragmentManager.putFragment(outState, "currentFragment", currentFragment)
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        val currentFragment = supportFragmentManager.findFragmentById(binding.framelayout.id)
//        if(currentFragment!=null){
//            outState.putString("currentFragment", currentFragment.tag)
//        }
//    }


}