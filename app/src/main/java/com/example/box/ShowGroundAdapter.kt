package com.example.box

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShowGroundAdapter : RecyclerView.Adapter<ShowGroundAdapter.ViewHolder>() {
    private var groundList: List<ImageInfo> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val address: TextView = itemView.findViewById(R.id.tvAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ground, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groundList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = groundList[position]
        holder.name.text = currentItem.boxName
        holder.address.text = currentItem.address
    }

    fun updateGroundList(newGroundList: List<ImageInfo>) {
        groundList = newGroundList
        notifyDataSetChanged()
    }
}
