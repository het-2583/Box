package com.example.box

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.box.databinding.ActivitySignUpBinding
import com.example.box.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.sbutton.setOnClickListener {
            val suser = binding.suser.text.toString()
            val email = binding.semail.text.toString()
            val pass = binding.spassword.text.toString()
            val cpass = binding.cpassword.text.toString()

            if (suser.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty()) {
                if (pass == cpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // User creation successful, now add additional info to Firestore
                                val userId = firebaseAuth.currentUser?.uid
                                userId?.let { uid ->
                                    val user = hashMapOf(
                                        "is_admin" to 1,  // Set is_admin to 1 for an admin user
                                        "email" to email,
                                        "username" to suser
                                    )

                                    firestore.collection("users").document(uid)
                                        .set(user)
                                        .addOnSuccessListener {
                                            binding.suser.text.clear()
                                            binding.semail.text.clear()
                                            binding.spassword.text.clear()
                                            binding.cpassword.text.clear()
                                            startActivity(Intent(this, SignInActivity::class.java))
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this,
                                                "Error adding user information to Firestore: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception?.message ?: "Authentication failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // Password strength checks go here
                        }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
