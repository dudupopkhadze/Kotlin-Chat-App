package com.example.client.MainScene

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.HistoryScene.HistoryActivity
import com.example.client.MessageScene.MessageActivity
import com.example.client.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MessageActivity::class.java)
        startActivity(intent)
    }
}