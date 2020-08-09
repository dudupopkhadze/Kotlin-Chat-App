package com.example.client.MainScene

import android.content.SharedPreferences

class MainScenePresenterImpl: MainSceneContract.Presenter {
    override fun getUsername(pref: SharedPreferences): String {
        val user = pref.getString("username", "")
        if(user==null || user.isEmpty()){
            return ""
        }

        return user
    }


}