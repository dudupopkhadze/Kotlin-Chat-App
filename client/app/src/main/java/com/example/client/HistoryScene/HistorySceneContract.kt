package com.example.client.HistoryScene

import android.content.SharedPreferences

interface HistorySceneContract {
    interface View{

    }

    interface Presenter {
        fun sendRequest(token: String)
        fun getToken(pref: SharedPreferences): String
    }
}