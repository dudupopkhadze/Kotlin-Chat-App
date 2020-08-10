package com.example.client.api.user

import com.google.gson.annotations.SerializedName

data class User (
        val userId: Long ,
        val nickName: String,
        val status: String,
        val profileImage:String? = null
)