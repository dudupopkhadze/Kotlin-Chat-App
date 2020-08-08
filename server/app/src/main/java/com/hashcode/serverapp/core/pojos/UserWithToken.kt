package com.hashcode.serverapp.core.pojos

import com.hashcode.serverapp.core.database.entities.User

data class UserWithToken(
    val user:User,
    val accessToken:String
)