package com.example.client.HistoryScene

import android.content.SharedPreferences
import android.util.Log

class HistoryScenePresenterImpl: HistorySceneContract.Presenter {

    override fun getToken(pref: SharedPreferences): String {
        val token = pref.getString("token", "")
        if(token==null || token.isEmpty()){
            return ""
        }

        return token
    }


}