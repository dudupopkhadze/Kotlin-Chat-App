package com.hashcode.serverapp.core.api

import android.content.Context
import android.util.Log
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
            // POST request method
            when (exchange!!.requestMethod) {
                "POST" -> searchWithNickname(exchange)
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
                Log.println(Log.DEBUG,"token",sqlQuery)
                val result = mutableListOf<UserWithLastMessage>()
                val users = appDatabase.userDao().findByName(sqlQuery)
                Log.println(Log.DEBUG,"kenth",users.size.toString())
                users.forEach {
                    val convo = appDatabase.conversationDao().getConversationForTwoUsers(user.userId,it.userId)
                    if(convo != null){
                        val messages = appDatabase.conversationDao().getConversationWithMessages(convo.conversationId)
                        if(messages != null) result.add(UserWithLastMessage(it,convo.conversationId,messages.messages[messages.messages.lastIndex].text))
                    } else {
                        if(user.userId != it.userId) {
                            result.add((UserWithLastMessage(it)))
                        }
                    }

                }
                Response.successfulRequestResponse(exchange,Gson().toJson(SearchResult(result)))
            }
        }
    }
}