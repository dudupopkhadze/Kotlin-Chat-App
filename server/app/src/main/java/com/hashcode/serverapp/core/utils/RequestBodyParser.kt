package com.hashcode.serverapp.core.utils

import android.util.Log
import com.hashcode.serverapp.core.api.schemas.requests.PostSendMessage
import com.hashcode.serverapp.core.database.entities.User
import java.io.InputStream
import java.util.*


object RequestBodyParser {
    fun parseCreateUserRequest(requestBody:InputStream): User? {
        val body = streamToString(requestBody)
        Log.println(Log.DEBUG,"msdsg",body)
        if(!ValidateRequestBody.isValidCreateUserRequest(body)){
            return null
        }
        return if(body.contains("profileImage")){
            User(nickName = getValueFromkey("nickName",body,true),
                status = getValueFromkey("status",body,true),
                    profileImage = getValueFromkey("profileImage",body,true))
        } else {
            User(nickName = getValueFromkey("nickName",body,true),
                status = getValueFromkey("status",body,true))
        };
    }

    fun parseGetUserByIdRequest(requestBody:InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidGetUserByIdRequest(body)){
            return null
        }
        return getValueFromkey("userId",body,false).toLongOrNull()
    }

    fun parseSearchWithNickname(requestBody: InputStream):String?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidSearchWithNicknameRequest(body)){
            return null
        }
        return getValueFromkey("query",body,true)
    }

    fun parseSendMessageRequest(requestBody: InputStream):PostSendMessage?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidSendMessageRequest(body)){
            return null
        }
        return PostSendMessage(
            conversationId = if(body.contains("conversationId")) getValueFromkey("conversationId",body,false).toLong() else null,
            messageText = getValueFromkey("messageText",body,true),
            receiverId = getValueFromkey("receiverId",body,false).toLong(),
            senderId = getValueFromkey("senderId",body,false).toLong())
    }

    fun parseGetConversationRequest(requestBody: InputStream):Long?{
        val body = streamToString(requestBody)

        Log.println(Log.DEBUG,"Sdsdsd",body)

        if(!ValidateRequestBody.isValidGetConversationRequest(body)){
            return null
        }

        Log.println(Log.DEBUG,"Sdsdsd","Asasasasas")

        return getValueFromkey("conversationId",body,false).toLong()
    }

    fun parseDeleteConversationByIdRequest(requestBody:InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidDeleteConversationByIdRequest(body)){
            return null
        }
        return getValueFromkey("conversationId",body,false).toLongOrNull()
    }

    private fun getValueFromkey(key:String,json:String,isStringValue:Boolean ):String{
        val offset = if(isStringValue) 3 else 2
        val startOfValue: Int = json.indexOf(key) + offset +key.length
        val temp: String = json.substring(startOfValue)
        var b = 0
        if (isStringValue){
            b =  temp.indexOf("\"")
        }else if (temp.indexOf(",") != -1){
            b = temp.indexOf(",")
        }else b = temp.indexOf("}")

        return temp.substring(0, b)
    }

    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}