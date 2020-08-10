package com.hashcode.serverapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.hashcode.serverapp.core.api.ConversationController
import com.hashcode.serverapp.core.api.SearchController
import com.hashcode.serverapp.core.api.UserController
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var serverUp = false
    private lateinit var userController: UserController
    private lateinit var conversationController: ConversationController
    private lateinit var searchController: SearchController
    private var mHttpServer: HttpServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userController= UserController(applicationContext)
        conversationController = ConversationController(applicationContext)
        searchController = SearchController(applicationContext)
        setSupportActionBar(toolbar)
        val port = 5000

        serverButton.setOnClickListener {
            serverUp = if(!serverUp){
                startServer(port)
                true
            } else{
                stopServer()
                false
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    private fun sendResponse(httpExchange: HttpExchange, responseText: String){
        httpExchange.sendResponseHeaders(200, responseText.length.toLong())
        val os = httpExchange.responseBody
        os.write(responseText.toByteArray())
        os.close()
    }

    private fun startServer(port: Int) {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer!!.executor = Executors.newCachedThreadPool()
            mHttpServer!!.createContext("/", rootHandler)
            mHttpServer!!.createContext("/test", userController.test)
            mHttpServer!!.createContext("/messages", messageHandler)

            //search
            mHttpServer!!.createContext("/search", searchController.search)

            //convos stuff
            mHttpServer!!.createContext("/convos/delete", conversationController.delete)

            //users stuff
            mHttpServer!!.createContext("/get-history",userController.getUserHistory)
            mHttpServer!!.createContext("/users/get", userController.getAllUsers)
            mHttpServer!!.createContext("/users/get-by-id", userController.getById)
            mHttpServer!!.createContext("/users/add", userController.create)

            //start server
            mHttpServer!!.start()//startServer server;
            serverTextView.text = getString(R.string.server_running)
            serverButton.text = getString(R.string.stop_server)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopServer() {
        if (mHttpServer != null){
            mHttpServer!!.stop(0)
            serverTextView.text = getString(R.string.server_down)
            serverButton.text = getString(R.string.start_server)
        }
    }

    // Handler for root endpoint
    private val rootHandler = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, "Welcome to my server")
                }

            }
        }

    }

    private val messageHandler = HttpHandler { httpExchange ->
        run {
            when (httpExchange!!.requestMethod) {
                "GET" -> {
                    // Get all messages
                    sendResponse(httpExchange, "Would be all messages stringified json")
                }
                "POST" -> {
                    val inputStream = httpExchange.requestBody

                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)
                    // save message to database

                    //for testing
                    sendResponse(httpExchange, jsonBody.toString())

                }
            }
        }
    }
}
