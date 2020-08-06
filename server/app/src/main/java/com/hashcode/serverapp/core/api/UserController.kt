package com.hashcode.serverapp.core.api

import com.hashcode.serverapp.core.database.AppDatabase
import com.hashcode.serverapp.core.database.entities.User
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hashcode.serverapp.core.services.AuthService
import com.hashcode.serverapp.core.services.UserService
import com.hashcode.serverapp.core.utils.RequestBodyParser
import com.hashcode.serverapp.core.utils.ValidateRequestBody
import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import org.json.JSONObject
import java.io.InputStream
import java.util.*

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

    private fun getUserById(exchange: HttpExchange){

        val id = RequestBodyParser().parseGetUserByIdRequest(exchange.requestBody)
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
        val user = RequestBodyParser().parseCreateUserRequest(exchange.requestBody)
        GlobalScope.launch {
            if(user != null){
                val id = appDatabase.userDao().insertUser(user)
                Response.sendResponse(exchange, 200,Gson().toJson(UserService.copyUserWithNewId(id,user)))
            }else{
                Response.badRequestResponse(exchange)
            }
        }
    }
}