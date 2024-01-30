package com.example.box

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.box.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etSinInEmail.text.toString()
            val pass = binding.etSinInPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            // Check if the user is an admin
                            checkAdminStatus()
                        } else {
                            Toast.makeText(
                                this,
                                "Sign-in failed: ${signInTask.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
            if (pass.length < 8) {
                Toast.makeText(this, "Minimum 8 character password required!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAdminStatus() {
        val userId = firebaseAuth.currentUser?.uid

        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document: DocumentSnapshot? = task.result
                        if (document != null && document.exists()) {
                            val isAdmin = document.getLong("is_admin")
                            if (isAdmin?.toInt() == 1) {
                                // Admin login successful, navigate to AdminDashboardActivity
                                val intent = Intent(this, ForgetPasswordActivity::class.java)
                                startActivity(intent)
                            } else {
                                // Regular user login successful, navigate to MainActivity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Error checking admin status: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            // If a user is already signed in, check if it's an admin and navigate accordingly
            checkAdminStatus()
        }
    }
}
