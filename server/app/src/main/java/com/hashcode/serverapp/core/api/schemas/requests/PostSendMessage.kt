package com.hashcode.serverapp.core.api.schemas.requests

data class PostSendMessage(
    val conversationId:Long? = null,
    val messageText:String,
    val senderId:Long,
    val receiverId:Long
)