package com.example.client.MessageScene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.api.user.GetConversationResponse
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageActivity : AppCompatActivity(), MessageSceneContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        recycler_message.layoutManager = LinearLayoutManager(this)

        val convo = intent.getLongExtra("convoID", -1)
        showMessages(convo)

    }

    override fun showMessages(convoID: Long) {
        if(convoID != (-1).toLong()){
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val x = retrofit.create(MessageSceneContract.APIconvos::class.java)
//            x.getConvos(convoID).also {
//                it.enqueue(object :retrofit2.Callback<GetConversationResponse> {
//                    override fun onFailure(call: Call<GetConversationResponse>, t: Throwable) {
//                        Log.d("FAILLLL", "Response FAILED")
//                    }
//
//                    override fun onResponse(
//                        call: Call<GetConversationResponse>,
//                        response: Response<GetConversationResponse>
//                    ) {
//                        val ls = response.body()
//                        Log.d("msgrsp", ls.toString())
//
//                        GlobalScope.launch {
//                            runOnUiThread{
//                                recycler_message.adapter = ls?.let { it1 -> MessageAdapter(it1) }
//                                recycler_message.smoothScrollToPosition((recycler_message.adapter as MessageAdapter).itemCount)
//                            }
//                        }
//                    }
//
//                })
//            }
        }

//        GlobalScope.launch {
//            runOnUiThread{
//                recycler_message.adapter = MessageAdapter()
//                recycler_message.smoothScrollToPosition((recycler_message.adapter as MessageAdapter).itemCount)
//            }
//        }
    }
}