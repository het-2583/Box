package com.example.box

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.box.databinding.ActivityGetStartedBinding
import com.example.box.SignInActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth

class GetStartedActivity : AppCompatActivity() {
    private var binding: ActivityGetStartedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@GetStartedActivity,SignInActivity::class.java)
            startActivity(intent)
        },3000)
    }


}