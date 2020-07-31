package com.hashcode.serverapp.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ConversationWithMessages(
    @Embedded val conversation:Conversation ,
    @Relation(
        parentColumn = "conversationId",
        entityColumn = "conversationId"
    )
    val messages: List<Message>
)
