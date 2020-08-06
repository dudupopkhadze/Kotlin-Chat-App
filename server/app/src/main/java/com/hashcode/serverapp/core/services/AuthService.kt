package com.hashcode.serverapp.core.services

import android.util.Log
import com.google.gson.Gson
import com.hashcode.serverapp.core.database.entities.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey


object AuthService {
    private const val KEY = "IrAl42feVTUeu8TFSFDLmabfhVLU+8NR6TxFeah9Y7U="
    private var key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY))

    fun generateAccessToken(user:User): String {
        return Jwts.builder().setSubject(Gson().toJson(user)).signWith(key).compact()
    }

    fun getUserFromAccessToken(token:String):User{
        val parsed = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
        return Gson().fromJson(parsed,User::class.java) as User
    }
}