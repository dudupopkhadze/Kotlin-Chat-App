package com.example.client.HistoryScene

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.IntroduceScene.IntroduceSceneContract
import com.example.client.R
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    lateinit var presenter: HistorySceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        presenter = HistoryScenePresenterImpl()

        val sharedPref = getSharedPreferences("bla", Context.MODE_PRIVATE)

        val token = presenter.getToken(sharedPref)

        if(token.isNotEmpty()){
            presenter.sendRequest(token)
        }

        recycler_history.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            runOnUiThread{
                recycler_history.adapter =
                    HistoryAdapter()
            }
        }

    }
}