package com.hashcode.serverapp.core.api

import com.hashcode.serverapp.core.database.entities.User
import com.hashcode.serverapp.core.services.AuthService
import com.sun.net.httpserver.HttpExchange
import java.lang.Exception

class AuthController {
    fun getUserFromToken(exchange:HttpExchange):User?{
        val headers = exchange.requestHeaders
        if(headers.containsKey("access-token")){
            return null
        }
        val token = headers["access-token"]?.get(0) ?: return null
        return try {
            AuthService.getUserFromAccessToken(token)
        }catch (e:Exception){
            null
        }
    }
}