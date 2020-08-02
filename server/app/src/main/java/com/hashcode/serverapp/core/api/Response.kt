package com.hashcode.serverapp.core.api

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange

class Response {
    fun sendResponse(httpExchange: HttpExchange, responseCode:Int, responseText: String,headers: Headers? = null){
        httpExchange.sendResponseHeaders(responseCode, responseText.length.toLong())
        val os = httpExchange.responseBody
        if (headers != null){
            httpExchange.requestHeaders.putAll(headers)
        }
        os.write(responseText.toByteArray())
        os.close()
    }
}