package com.example.client.HistoryScene

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.MessageScene.MessageActivity
import com.example.client.R
import com.example.client.api.user.ConversationPreview
import kotlinx.android.synthetic.main.history_cell.view.*

class HistoryAdapter(private val history: List<ConversationPreview>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.history_cell, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return history.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val con = history[position]

        val lastMessage = con.lastMessage
        val name = con.secondUser.nickName
        val img = con.secondUser.profileImage
        val convoID = con.conversation.conversationId

        holder.view.nametext.text = name
        holder.view.messagetext.text = lastMessage
        holder.view.datetext.text = "5 min"

//        if (img != null) {
//            if(img.isNotEmpty()){
//                holder.view.profile_image.set
//            }
//        }

        holder.view.setOnClickListener{
            val context = holder.view.context
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("convoID", convoID)
            context.startActivity(intent)
        }

    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}