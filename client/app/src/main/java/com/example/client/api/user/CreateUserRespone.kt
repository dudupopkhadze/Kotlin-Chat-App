package com.example.client.api.user

data class CreateUserResponse(
        val user:User,
        val accessToken:String
)