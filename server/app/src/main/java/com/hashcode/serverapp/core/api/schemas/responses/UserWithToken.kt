package com.hashcode.serverapp.core.api.schemas.responses

import com.hashcode.serverapp.core.database.entities.User

data class UserWithToken(
    val user:User,
    val accessToken:String
)