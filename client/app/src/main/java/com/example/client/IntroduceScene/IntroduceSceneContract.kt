package com.example.client.IntroduceScene

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IntroduceSceneContract {

    interface View{
        fun register(username: String, status: String, img: String)
    }

    interface Presenter {
        fun isFieldEmpty(username: String?, doing: String?): Boolean
    }

    interface APIlogin{
        @Headers("Content-Type: application/json")
        @POST("users")
        fun addUser(@Body userData: UserInfo): Call<UserInfo>
    }

    data class UserInfo (
        @SerializedName("username") val userName: String?,
        @SerializedName("status") val status: String?,
        @SerializedName("img") val img: String?
    )

}