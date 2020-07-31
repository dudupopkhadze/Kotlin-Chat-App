package com.hashcode.serverapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val messageId: Long,
    val conversationId:Long,
    val text:String,
    val sender_id:Int,
    val receiver_id:Int,
    val send_date:Date
)