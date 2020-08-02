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
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hashcode.serverapp.core.services.AuthService
import com.sun.net.httpserver.Headers
import org.json.JSONObject
import java.io.InputStream
import java.util.*

class UserController( context:Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    val insertUser = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "POST" -> {
                    GlobalScope.launch {
                        Log.println(Log.DEBUG,"user","post")
                        val inputStream = exchange.requestBody

                        var requestBody = streamToString(inputStream)
                        val gson  = Gson()
                        val jsonBody = gson.toJson(requestBody.trim())
                        Log.println(Log.DEBUG,"user",jsonBody)
                        Log.println(Log.DEBUG,"user",requestBody.length.toString())

                        val user = gson.fromJson(jsonBody,User::class.java)
                        Log.println(Log.DEBUG,"user",user.toString())

//                            AppDatabase.getInstance(context)
//                                .userDao()
//                                .insertUser(user)
//                        val headers = Headers()
//                        headers.add("access-token",AuthService().generateAccessToken(user))
                        Response().sendResponse(exchange, 200,"done")
                    }
                }


                else -> return@HttpHandler
            }
        }

    }

    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
