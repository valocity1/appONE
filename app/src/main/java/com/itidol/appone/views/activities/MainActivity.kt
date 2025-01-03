package com.itidol.appone.views.activities

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itidol.appone.R
import com.itidol.appone.databinding.ActivityMainBinding
import com.itidol.appone.views.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

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
        val attachedFragment = supportFragmentManager.beginTransaction()
        attachedFragment.replace(binding.framelayout.id, FirstFragment()).commit()
    }

    fun getFragments(fragments: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(binding.framelayout.id, fragments)
        fragment.addToBackStack(null)
        fragment.commit()
    }
}