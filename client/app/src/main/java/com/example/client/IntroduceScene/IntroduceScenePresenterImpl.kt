package com.example.client.IntroduceScene

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_introduce.*

class IntroduceScenePresenterImpl: IntroduceSceneContract.Presenter {
    override fun isFieldEmpty(username: String?, doing: String?): Boolean {
        return username == null || username.isEmpty() || doing==null || doing.isEmpty()
    }
}