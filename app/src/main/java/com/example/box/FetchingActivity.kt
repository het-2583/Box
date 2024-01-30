package com.example.box

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.box.databinding.ActivityFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.auth.User

class FetchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var DatabaseReference : DatabaseReference
    private lateinit var dialog : Dialog
    private lateinit var Database: database
    private lateinit var uid : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ActivityFetchingBinding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(ActivityFetchingBinding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()){
            getUserData()
        }

    }
    private fun getUserData(){
        DatabaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                Database = snapshot.getValue(database::class.java)!!
//                binding.upuser.text.toString()
//                binding.upemail.text.toString()
//                binding.upuser.setText(Database.name)
//                binding.upemail.setText(Database.email)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    private fun showProgressBar() {
//        dialog = Dialog(context:this@FetchingActivity)
//        dialog = requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.show()
//    }
//    private fun hideProgressBar() {
//        dialog.show()
//    }
}