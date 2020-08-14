package com.example.client.HistoryScene

import android.content.SharedPreferences
import com.example.client.api.user.SearchResult
import com.example.client.api.user.UserConversationsHistoryResponse
import com.example.client.api.user.searchRequest
import retrofit2.Call
import retrofit2.http.*

interface HistorySceneContract {
    interface View{
        fun sendRequest(token: String)
        fun showSearch(token: String, keyword: String)
    }

    interface Presenter {
        fun getToken(pref: SharedPreferences): String
    }

    interface APIhistory{
        @GET("get-history")
        fun getHistory(@Header("access-token") token: String): Call<UserConversationsHistoryResponse>
    }

    interface APIsearch{
        @POST("search")
        fun getSearch(@Body req: searchRequest, @Header("access-token") token: String): Call<SearchResult>
    }
}