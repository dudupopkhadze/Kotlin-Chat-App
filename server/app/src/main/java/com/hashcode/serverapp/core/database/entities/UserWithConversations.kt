package com.hashcode.serverapp.core.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithConversations(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "conversationId",
        associateBy = Junction(UserConversationCrossRef::class)
    )
    val conversations: List<Conversation>
)