package com.hashcode.serverapp.core.pojos

import com.hashcode.serverapp.core.database.entities.User

data class UserConversationsHistory (
    val user:User,
    val history: List<ConversationPreview>
)