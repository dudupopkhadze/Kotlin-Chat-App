package com.hashcode.serverapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val nickName: String,
    val status: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val profileImage:ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false
        if (nickName != other.nickName) return false
        if (status != other.status) return false
        if (profileImage != null) {
            if (other.profileImage == null) return false
            if (!profileImage.contentEquals(other.profileImage)) return false
        } else if (other.profileImage != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + (nickName.hashCode())
        result = 31 * result + (status.hashCode())
        result = 31 * result + (profileImage?.contentHashCode() ?: 0)
        return result
    }
}