package com.hashcode.serverapp.core.api

import com.hashcode.serverapp.core.database.AppDatabase
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import android.content.Context
import android.util.Log
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

    val test = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET"-> test(exchange)
                else -> Response.badRequestResponse(exchange)
            }
        }
    }

    private fun test(httpExchange: HttpExchange){
        GlobalScope.launch {
            val res = appDatabase.userDao().findByName("\'%kapka%\'")
            Log.println(Log.DEBUG,"msg",res.size.toString() )
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
        GlobalScope.launch {

            val user = AuthController().getUserFromToken(exchange)
            if(user == null){
                Response.notAuthenticatedResponse(exchange)
                return@launch
            }
            val userWithConversations = appDatabase.conversationDao().getAll().filter { it.firstUserId == user.userId || it.secondUserId==user.userId }
            Log.println(Log.DEBUG,"Sdsdsd",userWithConversations.toString())
            val convosHistory = mutableListOf<ConversationPreview>()
            if(userWithConversations != null){
                userWithConversations?.forEach {
                    Log.println(Log.DEBUG,"sdsddsd",it.toString())
                    val convosWithMessages = appDatabase.conversationDao().getConversationWithMessages(it.conversationId)
                    if(convosWithMessages != null){
                        val otherUserId =
                            if(user.userId == convosWithMessages.conversation.firstUserId)
                                convosWithMessages.conversation.secondUserId
                            else
                                convosWithMessages.conversation.firstUserId
                        val secondUser = appDatabase.userDao().findById(otherUserId)
                        if(secondUser != null) {
                            convosHistory.add(
                                ConversationPreview(
                                    conversation = it,
                                    lastMessage = convosWithMessages.messages[convosWithMessages.messages.lastIndex].text,
                                    secondUser = secondUser
                                )
                            )
                        }
                    }
                }
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
            if(user == null){
                Response.notFoundResponse(exchange)
            }else {
                Response.successfulRequestResponse(exchange, Gson().toJson(user))
            }
        }

    }

    private fun getAllUsers(exchange: HttpExchange){
        GlobalScope.launch {
            val users = appDatabase.userDao().getAll()
            if(users == null){
                Response.notFoundResponse(exchange)
            }else{
                val toJson = Gson().toJson(users)
                Response.successfulRequestResponse(exchange, toJson.toString())
            }

        }

    }

    private fun createUser(exchange: HttpExchange){
        Log.println(Log.DEBUG,"msdsg","asadfsdas")
        val user = RequestBodyParser.parseCreateUserRequest(exchange.requestBody)
        GlobalScope.launch {
            if(user != null){
                val id = appDatabase.userDao().insertUser(user)
                val newUser = UserService.copyUserWithNewId(id,user)
                val vl =  Gson().toJson(
                        UserWithToken(
                                user = newUser,
                                accessToken = AuthService.generateAccessToken(newUser)
                        )
                )
                Log.println(Log.DEBUG,"new",vl)

                Response.sendResponse(exchange, 200,
                    vl)
            }else{
                Response.badRequestResponse(exchange)
            }
        }
    }
}
