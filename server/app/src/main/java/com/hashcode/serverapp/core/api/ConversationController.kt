package com.hashcode.serverapp.core.api

import android.content.Context
import com.hashcode.serverapp.core.database.AppDatabase
import com.hashcode.serverapp.core.utils.RequestBodyParser
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConversationController(private val context: Context) {
    private val appDatabase = AppDatabase.getInstance(context)
    val delete = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "DELETE" -> delete(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun delete(exchange: HttpExchange){
        if(!AuthController().isAuthenticated(exchange)){
            Response.notAuthenticatedResponse(exchange)
        }
        val conversationId = RequestBodyParser.parseDeleteConversationByIdRequest(exchange.requestBody)
        if(conversationId == null){
            Response.badRequestResponse(exchange)
            return
        }
        GlobalScope.launch {
            val dao = appDatabase.conversationDao()
            val conversation = dao.findById(conversationId)
            if(conversation == null){
                Response.notFoundResponse(exchange)
            }else {
                dao.deleteConversation(conversation)
                Response.successfulRequestResponse(exchange, "Deleted")
            }
        }
    }
    private fun sendMessage(exchange: HttpExchange){

    }
}