package com.hashcode.serverapp.core.database.daos

import androidx.room.*
import com.hashcode.serverapp.core.database.entities.Conversation
import com.hashcode.serverapp.core.database.entities.ConversationWithMessages
import com.hashcode.serverapp.core.database.entities.User

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations")
    fun getAll():List<Conversation>

    @Query("SELECT * FROM  conversations WHERE conversationId == :conversationId")
    fun findById(conversationId:Long): Conversation

    @Transaction
    @Query("SELECT * FROM conversations WHERE conversationId == :conversationId")
    fun getConversationWithMessages(conversationId:Long):ConversationWithMessages

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversation(conversation: Conversation):Long

    @Delete()
    fun deleteConversation(conversation: Conversation)
}