package com.hashcode.serverapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val messageId: Long,
    @ColumnInfo(name = "text") val text:String,
    @ColumnInfo(name = "sender_id") val sender_id:Int,
    @ColumnInfo(name = "sender_id") val receiver_id:Int,
    @ColumnInfo(name = "send_date") val send_date:Date
)