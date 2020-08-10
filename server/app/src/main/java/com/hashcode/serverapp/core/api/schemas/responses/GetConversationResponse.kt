package com.hashcode.serverapp.core.api.schemas.responses

import com.hashcode.serverapp.core.database.entities.ConversationWithMessages
import com.hashcode.serverapp.core.database.entities.Message
import com.hashcode.serverapp.core.database.entities.User

data class GetConversationResponse(
    val conversationInfo:ConversationWithMessages,
    val partner:User
)