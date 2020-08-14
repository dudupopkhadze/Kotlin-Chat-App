package com.example.client.MessageScene

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.HistoryScene.CustomViewHolder
import com.example.client.R
import com.example.client.api.user.GetConversationResponse
import kotlinx.android.synthetic.main.message_cell_1.view.*

class MessageAdapter(private val convo: GetConversationResponse) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        if(viewType==1){
            val cellForRow = layoutInflater.inflate(R.layout.message_cell_2, parent, false)

            return CustomViewHolder(cellForRow)
        }

        val cellForRow = layoutInflater.inflate(R.layout.message_cell_1, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return convo.conversationInfo.messages.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val msgTxt = convo.conversationInfo.messages[position].text
        val dt = convo.conversationInfo.messages[position].send_date.toString()

        holder.view.messageID.text = msgTxt
        holder.view.timeID.text = dt
    }

    override fun getItemViewType(position: Int): Int {
        val receiver = convo.conversationInfo.messages[position].receiver_id
        val otherUser = convo.partner.userId

        if(receiver==otherUser){
            return 0
        }

        return 1
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}