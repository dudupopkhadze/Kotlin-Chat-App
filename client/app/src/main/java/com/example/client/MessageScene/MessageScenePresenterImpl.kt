package com.example.client.MessageScene

import android.content.SharedPreferences
import android.util.Log

class MessageScenePresenterImpl: MessageSceneContract.Presenter {
    override fun getToken(pref: SharedPreferences): String {
        val token = pref.getString("token", "")
        if(token==null || token.isEmpty()){
            return ""
        }

        return token
    }


}