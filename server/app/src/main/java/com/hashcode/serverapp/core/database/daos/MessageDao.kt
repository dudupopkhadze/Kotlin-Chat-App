package com.hashcode.serverapp.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hashcode.serverapp.core.database.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAll():List<Message>

    @Insert()
    fun insertMessage(message: Message)

    @Delete()
    fun deleteMessage(message: Message)
}