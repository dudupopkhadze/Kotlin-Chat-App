package com.example.client.HistoryScene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        recycler_history.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            runOnUiThread{
                recycler_history.adapter =
                    HistoryAdapter()
            }
        }

    }
}