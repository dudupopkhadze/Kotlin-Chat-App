package com.example.client.IntroduceScene

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.HistoryScene.HistoryActivity
import com.example.client.R
import kotlinx.android.synthetic.main.activity_introduce.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class IntroduceActivity : AppCompatActivity(), IntroduceSceneContract.View {
    lateinit var presenter: IntroduceSceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce)

        presenter = IntroduceScenePresenterImpl()

        startID.setOnClickListener{
            val username = usernameTextID.text
            val doing = whatidoTextID.text

            val isEmpty = presenter.isFieldEmpty(username.toString(), doing.toString())

            if(isEmpty){
                Toast.makeText(this, "Fill both fields!", Toast.LENGTH_LONG).show();
            } else{
                if(register(username.toString(), doing.toString(), "")){
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }

    override fun register(username: String, status: String, img: String): Boolean {
        val json: JSONObject = JSONObject()
        json.put("nickName", username)
        json.put("status", status)
        json.put("profileImage", img)



        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var res = false


        val x = retrofit.create(IntroduceSceneContract.APIlogin::class.java)
        val usercall = x.addUser(IntroduceSceneContract.UserInfo(username, status, img)).also {
            it.enqueue(object :retrofit2.Callback<IntroduceSceneContract.CreateUserResponse>{
                override fun onFailure(call: Call<IntroduceSceneContract.CreateUserResponse>, t: Throwable) {
                    Log.println(Log.DEBUG, "mdsd", t.message)
                    res = false
                }

                override fun onResponse(call: Call<IntroduceSceneContract.CreateUserResponse>, response: Response<IntroduceSceneContract.CreateUserResponse>) {
                    Log.println(Log.DEBUG,"mdsd", response.body().toString())
                    val token = response.body()?.accessToken
                    val user = response.body()?.user?.nickName

                    val sharedPref = getPreferences(Context.MODE_PRIVATE)
                    sharedPref.edit().putString("username", user).commit()
                    sharedPref.edit().putString("token", token).commit()

                    res = true
                }

            })
        }

        return true
    }
}