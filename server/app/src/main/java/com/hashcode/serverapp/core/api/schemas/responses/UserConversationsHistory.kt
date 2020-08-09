package com.hashcode.serverapp.core.api.schemas.responses

import com.hashcode.serverapp.core.api.schemas.responses.ConversationPreview
import com.hashcode.serverapp.core.database.entities.User

data class UserConversationsHistory (
    val user:User,
    val history: List<ConversationPreview>
)