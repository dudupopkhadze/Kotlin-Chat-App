package com.hashcode.serverapp.core.api.schemas.responses

import com.hashcode.serverapp.core.database.entities.User

data class UserWithLastMessage(
    val user:User,
    val lastMessage: String? = null
)