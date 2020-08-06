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

    fun isValidGetUserByIdRequest(requestBody: String):Boolean{
        return requestBody.contains("userId")
    }
}