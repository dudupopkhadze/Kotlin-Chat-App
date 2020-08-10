package com.example.client.MainScene

import android.content.SharedPreferences

interface MainSceneContract {
    interface View{
        fun showLoginOrHistory(pref: SharedPreferences)
    }

    interface Presenter {
        fun getUsername(pref: SharedPreferences): String
    }
}