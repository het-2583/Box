package com.example.box

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.box.databinding.ActivityMainBinding
import com.example.box.databinding.HomepageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val homepageBinding = HomepageBinding.inflate(layoutInflater)
        setContentView(homepageBinding.root)

        val signOutButton = homepageBinding.btnSignOut
        val updateProfile = homepageBinding.btnUpdateProfile


        auth = Firebase.auth
        signOutButton.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                startActivity(Intent(this, GetStartedActivity::class.java))
                finish()
            }



        }
        auth = Firebase.auth
        updateProfile.setOnClickListener {
            if (auth.currentUser != null) {
                startActivity(Intent(this, FetchingActivity::class.java))

            }
        }
    }

    override fun onCreateOptionsMenu(menu:Menu?): Boolean {
        menuInflater. inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}