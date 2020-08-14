package com.example.client.MessageScene

import android.content.SharedPreferences
import com.example.client.api.user.GetConversationResponse
import com.example.client.api.user.convoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MessageSceneContract {
    interface View{
        fun showMessages(convoID: Long, token: String)
    }

    interface Presenter {
        fun getToken(pref: SharedPreferences): String
    }

    interface APIconvos{
        @POST("convos/get")
        fun getConvos(@Body convo: convoRequest, @Header("access-token") token: String): Call<GetConversationResponse>
    }
}