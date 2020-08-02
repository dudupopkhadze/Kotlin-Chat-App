package com.hashcode.serverapp.core.api

import com.sun.net.httpserver.HttpExchange

class AuthController {
    fun isAuthenticated(exchange:HttpExchange):Boolean{
        val headers = exchange.requestHeaders
        return headers.containsKey("access-token")
    }
}