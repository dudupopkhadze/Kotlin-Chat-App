package com.hashcode.serverapp.core.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "conversationId"])
data class UserConversationCrossRef(
    val userId: Long,
    val conversationId: Long
)