package com.hashcode.serverapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("status")
    val status: String,
    val profileImage:String? = null
)