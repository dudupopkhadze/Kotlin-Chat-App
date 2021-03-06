package com.example.client.HistoryScene

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.api.user.SearchResult
import com.example.client.api.user.UserConversationsHistoryResponse
import com.example.client.api.user.searchRequest
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryActivity : AppCompatActivity(), HistorySceneContract.View {
    lateinit var presenter: HistorySceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        presenter = HistoryScenePresenterImpl()

        val sharedPref = getSharedPreferences("bla", Context.MODE_PRIVATE)

        val token = presenter.getToken(sharedPref)

        if(token.isNotEmpty()){
            sendRequest(token)
        }

        recycler_history.layoutManager = LinearLayoutManager(this)


        searchButt.setOnClickListener {

            val searchKeyword = searchTextID.text.toString()

            if(searchKeyword.isNotEmpty()){
                showSearch(token, searchKeyword)
            }

        }
    }

    override fun sendRequest(token: String) {
        Log.d("tokeniii", token)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val x = retrofit.create(HistorySceneContract.APIhistory::class.java)
        x.getHistory(token).also {
            it.enqueue(object :retrofit2.Callback<UserConversationsHistoryResponse>{
                override fun onFailure(call: Call<UserConversationsHistoryResponse>, t: Throwable) {
                    Log.d("FAILLLL", "Response FAILED")
                }

                override fun onResponse(
                    call: Call<UserConversationsHistoryResponse>,
                    response: Response<UserConversationsHistoryResponse>
                ) {
                    val ls = response.body()?.history
                    Log.d("RESPONSE", response.body().toString())

                    GlobalScope.launch {
                        runOnUiThread{
                            recycler_history.adapter = ls?.let { it1 -> HistoryAdapter(it1) }
                        }
                    }
                }

            })
        }
    }

    override fun showSearch(token: String, keyword: String) {
        Log.d("searchTOK", token)
        Log.d("seatchKEY", keyword)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val x = retrofit.create(HistorySceneContract.APIsearch::class.java)
        x.getSearch(searchRequest(keyword), token).also {
            it.enqueue(object :retrofit2.Callback<SearchResult>{
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    Log.d("FAILLLL", "Response FAILED")
                }

                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    val ls = response.body()?.results
                    Log.d("searchRESPONSE", response.body().toString())

                    GlobalScope.launch {
                        runOnUiThread{

                           recycler_history.adapter = ls?.let { it1 -> SearchAdapter(it1) }
                        }
                    }
                }

            })
        }
    }
}