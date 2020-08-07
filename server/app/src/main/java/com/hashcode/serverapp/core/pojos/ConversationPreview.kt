package com.hashcode.serverapp.core.pojos

import com.hashcode.serverapp.core.database.entities.Conversation

data class ConversationPreview(
    val conversation:Conversation,val lastMessage:String
)