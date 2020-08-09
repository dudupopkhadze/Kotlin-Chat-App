package com.example.client.MessageScene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.HistoryScene.HistoryAdapter
import com.example.client.R
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        recycler_message.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            runOnUiThread{
                recycler_message.adapter = MessageAdapter()
                recycler_message.smoothScrollToPosition((recycler_message.adapter as MessageAdapter).itemCount)
            }
        }
    }
}