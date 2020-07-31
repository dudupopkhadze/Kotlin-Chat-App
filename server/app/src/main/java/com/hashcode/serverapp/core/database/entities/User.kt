package com.hashcode.serverapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Long,
    @ColumnInfo(name = "nickname") val nickName: String?,
    @ColumnInfo(name = "status") val status: String?
)