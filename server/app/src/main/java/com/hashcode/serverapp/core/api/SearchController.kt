package com.hashcode.serverapp.core.api

import android.content.Context
import com.google.gson.Gson
import com.hashcode.serverapp.core.api.schemas.responses.SearchResult
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
                "GET" -> searchWithNickname(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun searchWithNickname(exchange: HttpExchange){
        val user = AuthController().getUserFromToken(exchange)
        if(user == null){
            Response.notAuthenticatedResponse(exchange)
            return
        }
        GlobalScope.launch {
            val query = RequestBodyParser.parseSearchWithNickname(exchange.requestBody)
            if(query == null) {
                Response.badRequestResponse(exchange)
            }else{
                val sqlQuery ="%$query%"
                val result = mutableListOf<UserWithLastMessage>()
                val users = appDatabase.userDao().findByName(sqlQuery)
                users.forEach {
                    val convo = appDatabase.conversationDao().getConversationForTwoUsers(user.userId,it.userId)
                    val messages = appDatabase.conversationDao().getConversationWithMessages(convo.conversationId)
                    result.add(UserWithLastMessage(it,messages.messages[messages.messages.lastIndex].text))
                }
                Response.successfulRequestResponse(exchange,Gson().toJson(SearchResult(result)))
            }
        }
    }
}