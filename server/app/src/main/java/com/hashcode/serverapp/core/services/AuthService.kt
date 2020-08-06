package com.hashcode.serverapp.core.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.hashcode.serverapp.core.database.entities.User
import io.fusionauth.jwt.Signer
import io.fusionauth.jwt.Verifier
import io.fusionauth.jwt.domain.JWT
import io.fusionauth.jwt.hmac.HMACSigner
import io.fusionauth.jwt.hmac.HMACVerifier
import java.time.ZonedDateTime


object AuthService {
    private val KEY = "Super Duper Secret Key"
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateAccessToken(user:User): String {
        val signer: Signer = HMACSigner.newSHA256Signer(KEY)

        val jwt = JWT().setSubject(Gson().toJson(user).toString()).setExpiration(ZonedDateTime.now().plusDays(300))
        return JWT.getEncoder().encode(jwt, signer);
    }

    fun getUserFromAccessToken(token:String):User{
        val verifier: Verifier = HMACVerifier.newVerifier("too many secrets")

        val jwt: JWT = JWT.getDecoder().decode(token, verifier)
        return Gson().fromJson(JsonParser().parse(jwt.subject).asJsonObject,User::class.java) as User
    }
}