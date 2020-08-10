package com.example.client.HistoryScene

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import kotlinx.android.synthetic.main.history_cell.view.*

class HistoryAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.history_cell, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.nametext.text = "John Doe"
        holder.view.messagetext.text = "On my way home but I needed to stop by the book store to..."
        holder.view.datetext.text = "5 min"
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}