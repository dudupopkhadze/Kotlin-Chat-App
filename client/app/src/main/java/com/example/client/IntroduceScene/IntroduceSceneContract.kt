package com.example.client.IntroduceScene

interface IntroduceSceneContract {

    interface View{
        fun register()
    }

    interface Presenter {
        fun isFieldEmpty(username: String?, doing: String?): Boolean
    }
}