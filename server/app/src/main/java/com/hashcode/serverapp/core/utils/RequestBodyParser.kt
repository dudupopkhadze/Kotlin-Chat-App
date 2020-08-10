package com.hashcode.serverapp.core.utils

import com.hashcode.serverapp.core.api.schemas.requests.PostSendMessage
import com.hashcode.serverapp.core.database.entities.User
import java.io.InputStream
import java.util.*


object RequestBodyParser {
    fun parseCreateUserRequest(requestBody:InputStream): User? {
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidCreateUserRequest(body)){
            return null
        }
        return if(body.contains("profileImage")){
            User(nickName = getValueFromkey("nickName",body),
                status = getValueFromkey("status",body),profileImage = getValueFromkey("profileImage",body))
        } else {
            User(nickName = getValueFromkey("nickName",body),
                status = getValueFromkey("status",body))
        };
    }

    fun parseGetUserByIdRequest(requestBody:InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidGetUserByIdRequest(body)){
            return null
        }
        return getValueFromkey("userId",body).toLongOrNull()
    }

    fun parseSearchWithNickname(requestBody: InputStream):String?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidSearchWithNicknameRequest(body)){
            return null
        }
        return getValueFromkey("query",body)
    }

    fun parseSendMessageRequest(requestBody: InputStream):PostSendMessage?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidSendMessageRequest(body)){
            return null
        }
        return PostSendMessage(
            conversationId = if(body.contains("conversationId")) getValueFromkey("conversationId",body).toLong() else null,
            messageText = getValueFromkey("messageText",body),
            receiverId = getValueFromkey("receiverId",body).toLong(),
            senderId = getValueFromkey("senderId",body).toLong())
    }

    fun parseGetConversationRequest(requestBody: InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidGetConversationRequest(body)){
            return null
        }
        return getValueFromkey("conversationId",body).toLongOrNull()
    }

    fun parseDeleteConversationByIdRequest(requestBody:InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidDeleteConversationByIdRequest(body)){
            return null
        }
        return getValueFromkey("conversationId",body).toLongOrNull()
    }

    private fun getValueFromkey(key:String,json:String):String{
        val startOfValue: Int = json.indexOf(key) + 3 +key.length
        val temp: String = json.substring(startOfValue)
        val endOfValue = temp.indexOf("\"")
        return temp.substring(0, endOfValue)
    }

    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}