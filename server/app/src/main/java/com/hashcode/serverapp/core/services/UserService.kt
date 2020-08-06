package com.hashcode.serverapp.core.services

import com.hashcode.serverapp.core.database.entities.User

object UserService {
    fun copyUserWithNewId(newId:Long,user:User):User{
        return User(userId = newId,status = user.status,
            nickName = user.nickName,
            profileImage = user.profileImage)
    }
}