package com.example.client.api.user


data class Conversation(
    val conversationId: Long,
    val firstUserId:Long,
    val secondUserId:Long
)

data class ConversationPreview(
    val conversation:Conversation,
    val lastMessage:String,
    val secondUser:User
)

data class UserConversationsHistoryResponse (
    val user: User,
    val history: List<ConversationPreview>
)

data class historyRequest(
    val messageText: String,
    val senderId:Long,
    val receiverId:Long
)