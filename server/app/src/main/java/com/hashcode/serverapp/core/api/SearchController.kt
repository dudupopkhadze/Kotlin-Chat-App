package com.hashcode.serverapp.core.api

import android.content.Context
import com.hashcode.serverapp.core.api.schemas.responses.UserWithLastMessage
import com.hashcode.serverapp.core.database.AppDatabase
import com.hashcode.serverapp.core.utils.RequestBodyParser
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchController (private val context: Context) {
    private val appDatabase = AppDatabase.getInstance(context)
    val search = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "POST" -> searchWithNickname(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun searchWithNickname(exchange: HttpExchange){
        val query = RequestBodyParser.parseSearchWithNickname(exchange.requestBody)
        if(query == null){
            Response.badRequestResponse(exchange)
            return
        }
        val sqlQuery ="%$query%"
        GlobalScope.launch {
            val result = mutableListOf<UserWithLastMessage>()
            val users = appDatabase.userDao().findByName(sqlQuery)


        }

    }
}