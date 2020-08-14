package com.example.client.MessageScene

import com.example.client.api.user.GetConversationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface MessageSceneContract {
    interface View{
        fun showMessages(convoID: Long)
    }

    interface Presenter {

    }

    interface APIconvos{
        @GET("convos/get")
        fun getConvos(@Body conversationId: Long): Call<GetConversationResponse>
    }
}