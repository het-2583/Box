package com.example.box

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import com.example.box.databinding.FragmentHomeBinding

import com.squareup.picasso.Picasso
import java.util.ArrayList

class ShowGround(private  val groundList: ArrayList<ImageInfo>) : RecyclerView.Adapter<ShowGround.ViewHolder>() {

    class ViewHolder(val binding : FragmentHomeBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return  ViewHolder(FragmentHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return groundList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = groundList[position]
        holder.apply {
            binding.apply {
                name.text = currentItem.boxName
                address.text = currentItem.address
                groundId.text = currentItem.groundId
                Picasso.get().load(currentItem.imageUrl).into(imgItem)


                rvContainer.setOnClickListener {
//
//                    val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
//                        currentItem.id.toString(),
//                        currentItem.name.toString(),
//                        currentItem.phoneNumber.toString(),
//                        currentItem.imgUri.toString()
//                    )
//                    findNavController(holder.itemView).navigate(action)
//                }

                rvContainer.setOnLongClickListener {
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Delete item permanently")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes"){_,_ ->
                            val firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
                            val storageRef = FirebaseStorage.getInstance().getReference("Images")
                            //storage
                            storageRef.child(currentItem.groundId.toString()).delete()

                            // realtime database
                            firebaseRef.child(currentItem.groundId.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(holder.itemView.context,"Item removed successfully" ,Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {error ->
                                    Toast.makeText(holder.itemView.context,"error ${error.message}" ,Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("No"){_,_ ->
                            Toast.makeText(holder.itemView.context,"canceled" ,Toast.LENGTH_SHORT).show()
                        }
                        .show()

                    return@setOnLongClickListener true
                }
            }
        }
    }
}}
