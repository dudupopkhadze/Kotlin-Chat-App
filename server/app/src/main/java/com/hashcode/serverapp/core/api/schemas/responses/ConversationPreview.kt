package com.hashcode.serverapp.core.api.schemas.responses

import com.hashcode.serverapp.core.database.entities.Conversation
import com.hashcode.serverapp.core.database.entities.User

data class ConversationPreview(
    val conversation:Conversation,
    val lastMessage:String,
    val secondUser:User
)