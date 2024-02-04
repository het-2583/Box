package com.example.box


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ShowGroundsActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var groundListAdapter: ShowGroundAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_grounds)

        // Assuming your grounds are stored under the "images" node in Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("images")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        groundListAdapter = ShowGroundAdapter()
        recyclerView.adapter = groundListAdapter

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val groundsList = mutableListOf<ImageInfo>()
                for (snapshot in dataSnapshot.children) {
                    val imageInfo = snapshot.getValue(ImageInfo::class.java)
                    if (imageInfo != null) {
                        groundsList.add(imageInfo)
                    }
                }
                groundListAdapter.updateGroundList(groundsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
