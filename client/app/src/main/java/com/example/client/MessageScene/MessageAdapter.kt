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
        return 10
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.messageID.text = "Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference. Kotlin is designed to interoperate fully with Java, and the JVM version of its standard library depends on the Java Class Library,[3] but type inference allows its syntax to be more concise. Kotlin mainly targets the JVM, but also compiles to JavaScript or native code (via LLVM). Language development costs are borne by JetBrains, while the Kotlin Foundation protects the Kotlin trademark."
        holder.view.timeID.text = "22:45"
    }

    override fun getItemViewType(position: Int): Int {
        if(position%2==0){
            return 0
        }

        return 1
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}