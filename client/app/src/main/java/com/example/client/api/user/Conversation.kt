package com.example.client.api.user

import java.util.*


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

data class GetConversationResponse(
    val conversationInfo: ConversationWithMessages,
    val partner:User
)

data class ConversationWithMessages(
    val conversation:Conversation ,
    val messages: List<Message>
)

data class Message(
    val messageId: Long = 0,
    val conversationId:Long,
    val text:String,
    val sender_id:Long,
    val receiver_id:Long,
    val send_date:Date
)