package com.hashcode.serverapp.core.database.daos

import androidx.room.*
import com.hashcode.serverapp.core.database.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAll():List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message):Long

    @Delete()
    fun deleteMessage(message: Message)
}