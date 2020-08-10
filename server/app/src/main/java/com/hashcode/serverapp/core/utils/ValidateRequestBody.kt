package com.hashcode.serverapp.core.utils

object ValidateRequestBody {
    fun isValidCreateUserRequest(requestBody: String):Boolean{
        if(!requestBody.contains("nickName")){
            return false
        }
        if(!requestBody.contains("status")){
            return false
        }
        return true
    }

    fun isValidSearchWithNicknameRequest(requestBody: String):Boolean{
        return requestBody.contains("query")
    }

    fun isValidGetUserByIdRequest(requestBody: String):Boolean{
        return requestBody.contains("userId")
    }

    fun isValidDeleteConversationByIdRequest(requestBody: String):Boolean{
        return requestBody.contains("conversationId")
    }

    fun isValidGetConversationRequest(requestBody: String):Boolean{
        return requestBody.contains("conversationId")
    }

    fun isValidSendMessageRequest(requestBody: String):Boolean{
        if(!requestBody.contains("messageText")){
            return false
        }
        if(!requestBody.contains("senderId")){
            return false
        }
        if(!requestBody.contains("receiverId")){
            return false
        }
        return true
    }
}