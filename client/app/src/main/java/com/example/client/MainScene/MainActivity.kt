package com.example.client.MainScene

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.client.IntroduceScene.IntroduceActivity
import com.example.client.MessageScene.MessageActivity
import com.example.client.R

class MainActivity : AppCompatActivity(), MainSceneContract.View {
    lateinit var presenter: MainSceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainScenePresenterImpl()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        //sharedPref.edit().putString("username", "").commit()
        showLoginOrHistory(sharedPref)
    }

    override fun showLoginOrHistory(pref: SharedPreferences) {
        val username = presenter.getUsername(pref)

        if(username.isEmpty()){
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        } else{
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
    }
}