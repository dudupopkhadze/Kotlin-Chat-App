package com.hashcode.serverapp.core.api

import android.util.Log
import com.hashcode.serverapp.core.database.entities.User
import com.hashcode.serverapp.core.services.AuthService
import com.sun.net.httpserver.HttpExchange
import java.lang.Exception

class AuthController {
    fun getUserFromToken(exchange:HttpExchange):User?{
        val headers = exchange.requestHeaders
        if(!headers.containsKey("Access-token")){
            return null
        }
        Log.println(Log.DEBUG,"token","akk")
        val token = headers["Access-token"]?.get(0) ?: return null
        return try {
            AuthService.getUserFromAccessToken(token)
        }catch (e:Exception){
            null
        }
    }

    fun isAuthenticated(exchange: HttpExchange):Boolean{
        return getUserFromToken(exchange) == null
    }
}