package com.hashcode.serverapp.core.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.hashcode.serverapp.core.api.schemas.responses.GetConversationResponse
import com.hashcode.serverapp.core.database.AppDatabase
import com.hashcode.serverapp.core.database.entities.Conversation
import com.hashcode.serverapp.core.database.entities.Message
import com.hashcode.serverapp.core.database.entities.User
import com.hashcode.serverapp.core.utils.RequestBodyParser
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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

    val sendMessage = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "POST" -> sendMessage(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }
    val getConvo = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET" -> getConversation(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun getConversation(exchange: HttpExchange){
        val user = AuthController().getUserFromToken(exchange)
        if(user == null){
            Response.notAuthenticatedResponse(exchange)
            return
        }
        val conversationId = RequestBodyParser.parseGetConversationRequest(exchange.requestBody)
        if (conversationId == null){
            Response.badRequestResponse(exchange)
            return
        }
        getConversationInfo(exchange,conversationId,user)
    }

    private fun getConversationInfo(exchange: HttpExchange,conversationId:Long,user:User){
        GlobalScope.launch {
            val conversation = appDatabase.conversationDao().getConversationWithMessages(conversationId)
            if(conversation != null){
                val otherUserId =
                    if(user.userId == conversation.conversation.firstUserId)
                        conversation.conversation.secondUserId
                    else
                        conversation.conversation.firstUserId
                val partner = appDatabase.userDao().findById(otherUserId)
                if(partner != null){
                    Response.successfulRequestResponse(exchange,Gson().toJson(
                        GetConversationResponse(conversationInfo = conversation,partner = partner)
                    ))
                }else Response.badRequestResponse(exchange)
            }else Response.notFoundResponse(exchange)
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
        GlobalScope.launch {
        val user = AuthController().getUserFromToken(exchange)
        if(user == null){
            Response.notAuthenticatedResponse(exchange)
            return@launch
        }
        Log.println(Log.DEBUG,"mdsg","priveli")
        val message = RequestBodyParser.parseSendMessageRequest(exchange.requestBody)

        if(message == null){
            Response.badRequestResponse(exchange)
            return@launch
        }
        Log.println(Log.DEBUG,"mdsg","m3o43W")

            if(message.conversationId != null){
                appDatabase.messageDao().insertMessage(Message(conversationId = message.conversationId,
                    receiver_id = message.receiverId,sender_id = message.senderId,text = message.messageText,send_date = Date()))
                getConversationInfo(exchange,message.conversationId,user)
            } else {
                val conversation = appDatabase.conversationDao().insertConversation(
                    Conversation(secondUserId = message.receiverId,firstUserId = message.senderId)
                )
                appDatabase.messageDao().insertMessage(Message(conversationId = conversation,
                    receiver_id = message.receiverId,sender_id = message.senderId,text = message.messageText,send_date = Date()))
                getConversationInfo(exchange,conversation,user)
            }

        }

    }
}