package com.hashcode.serverapp.core.api

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange

object Response {
    fun sendResponse(httpExchange: HttpExchange, responseCode:Int, responseText: String,headers: Headers? = null){
        httpExchange.sendResponseHeaders(responseCode, responseText.length.toLong())
        val os = httpExchange.responseBody
        if (headers != null){
            httpExchange.requestHeaders.putAll(headers)
        }
        os.write(responseText.toByteArray())
        os.close()
    }

    fun notFoundResponse(httpExchange: HttpExchange){
        sendResponse(httpExchange,404,"Not Found")
    }

    fun badRequestResponse(httpExchange: HttpExchange){
        sendResponse(httpExchange,400,"Bad Request")
    }

    fun successfulRequestResponse(httpExchange: HttpExchange,  responseText: String,headers: Headers? = null){
        sendResponse(httpExchange,200,responseText,headers)
    }
}