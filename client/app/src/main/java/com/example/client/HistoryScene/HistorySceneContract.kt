package com.example.client.HistoryScene

import android.content.SharedPreferences

interface HistorySceneContract {
    interface View{
        fun sendRequest(token: String)
    }

    interface Presenter {
        fun getToken(pref: SharedPreferences): String
    }
}