package com.example.client.api.user

import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("users/add")
    fun createUser(@Body user:CreateUser):CreateUserResponse
}