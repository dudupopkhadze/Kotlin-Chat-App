package com.hashcode.serverapp.core.utils

import android.R.attr
import android.util.Log
import com.hashcode.serverapp.core.database.entities.User
import java.io.InputStream
import java.util.*


class RequestBodyParser {
    fun parseCreateUserRequest(requestBody:InputStream): User? {
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidCreateUserRequest(body)){
            return null
        }
        return  User(nickName = getValueFromkey("nickName",body),
               status = getValueFromkey("status",body));
    }

    fun parseGetUserByIdRequest(requestBody:InputStream):Long?{
        val body = streamToString(requestBody)
        if(!ValidateRequestBody.isValidGetUserByIdRequest(body)){
            return null
        }
        return getValueFromkey("userId",body).toLongOrNull()
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