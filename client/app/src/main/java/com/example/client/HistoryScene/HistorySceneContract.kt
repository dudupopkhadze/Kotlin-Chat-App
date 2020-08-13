package com.example.client.HistoryScene

import android.content.SharedPreferences
import com.example.client.api.user.UserConversationsHistoryResponse
import com.example.client.api.user.historyRequest
import retrofit2.Call
import retrofit2.http.*

interface HistorySceneContract {
    interface View{
        fun sendRequest(token: String)
    }

    interface Presenter {
        fun getToken(pref: SharedPreferences): String
    }

    interface APIhistory{
        //@Header("access-token")
        @GET("get-history")
        fun getHistory(@Header("access-token") token: String): Call<UserConversationsHistoryResponse>
    }
}