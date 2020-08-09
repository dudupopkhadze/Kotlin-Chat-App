package com.hashcode.serverapp.core.api

import com.hashcode.serverapp.core.database.AppDatabase
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import android.content.Context
import com.google.gson.Gson
import com.hashcode.serverapp.core.api.schemas.responses.ConversationPreview
import com.hashcode.serverapp.core.api.schemas.responses.UserConversationsHistory
import com.hashcode.serverapp.core.api.schemas.responses.UserWithToken
import com.hashcode.serverapp.core.services.AuthService
import com.hashcode.serverapp.core.services.UserService
import com.hashcode.serverapp.core.utils.RequestBodyParser
import com.sun.net.httpserver.HttpExchange

class UserController(private val context: Context) {
    private val appDatabase = AppDatabase.getInstance(context)
    val create = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "POST" -> createUser(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    val getById = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET" -> getUserById(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    val getAllUsers = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> getAllUsers(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    val getUserHistory = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> getUserConversationsHistory(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun getUserConversationsHistory(exchange:HttpExchange){
        val user = AuthController().getUserFromToken(exchange)
        if(user == null){
            Response.notAuthenticatedResponse(exchange)
            return
        }
        GlobalScope.launch {
            val userWithConversations = appDatabase.userDao().getUserWithConversations(user.userId)
            val convosHistory = mutableListOf<ConversationPreview>()
            userWithConversations.conversations.forEach {
                val convosWithMessages = appDatabase.conversationDao().getConversationWithMessages(it.conversationId)
                convosHistory.add(
                    ConversationPreview(
                        conversation = it,
                        lastMessage = convosWithMessages.messages[convosWithMessages.messages.lastIndex].text
                    )
                )
            }
            Response.successfulRequestResponse(exchange,Gson().toJson(
                UserConversationsHistory(
                    user,
                    convosHistory
                )
            ))
        }
    }

    private fun getUserById(exchange: HttpExchange){
        val id = RequestBodyParser.parseGetUserByIdRequest(exchange.requestBody)
        if(id == null){
            Response.notFoundResponse(exchange)
            return
        }
        GlobalScope.launch {
            val user =  appDatabase.userDao().findById(id)
            Response.successfulRequestResponse(exchange,Gson().toJson(user))
        }

    }

    private fun getAllUsers(exchange: HttpExchange){
        val users = appDatabase.userDao().getAll()
        val toJson = Gson().toJson(users)
        Response.successfulRequestResponse(exchange, toJson.toString())
    }

    private fun createUser(exchange: HttpExchange){
        val user = RequestBodyParser.parseCreateUserRequest(exchange.requestBody)
        GlobalScope.launch {
            if(user != null){
                val id = appDatabase.userDao().insertUser(user)
                val newUser = UserService.copyUserWithNewId(id,user)
                Response.sendResponse(exchange, 200,
                    Gson().toJson(
                        UserWithToken(
                            user = newUser,
                            accessToken = AuthService.generateAccessToken(newUser)
                        )
                    ))
            }else{
                Response.badRequestResponse(exchange)
            }
        }
    }
}
