package com.hashcode.serverapp.core.database.daos

import androidx.room.*
import com.hashcode.serverapp.core.database.entities.User
import com.hashcode.serverapp.core.database.entities.UserWithConversations

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll():List<User>

    @Query("SELECT * FROM  users WHERE userId == :userId")
    fun findById(userId:Long):User

    @Query("SELECT * FROM  users WHERE userId == :userId")
    fun getUserWithConversations(userId:Long):UserWithConversations

    @Query("SELECT * FROM users WHERE nickName LIKE :nickName")
    fun findByName(nickName: String):List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertUser(user:User):Long

    @Delete
    fun deleteUser(user:User)
}