package com.example.client.IntroduceScene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.client.R
import kotlinx.android.synthetic.main.activity_introduce.*

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
                register()
            }

        }
    }

    override fun register() {
        // if nickname is already taken
        Toast.makeText(this, "Nickname is already taken", Toast.LENGTH_LONG).show();

        // else register
    }
}