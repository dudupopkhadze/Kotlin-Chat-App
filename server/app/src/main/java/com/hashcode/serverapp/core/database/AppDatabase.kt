package com.hashcode.serverapp.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hashcode.serverapp.core.database.converters.DateConverter
import com.hashcode.serverapp.core.database.daos.ConversationDao
import com.hashcode.serverapp.core.database.daos.MessageDao
import com.hashcode.serverapp.core.database.daos.UserDao
import com.hashcode.serverapp.core.database.entities.Conversation
import com.hashcode.serverapp.core.database.entities.Message
import com.hashcode.serverapp.core.database.entities.User
import com.hashcode.serverapp.core.database.entities.UserConversationCrossRef
import java.lang.ref.WeakReference

@Database(entities = [User::class, Message::class, Conversation::class, UserConversationCrossRef::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase :RoomDatabase(){

    companion object {
        private lateinit var showcaseDatabase: AppDatabase
        private var initialized: Boolean = false

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (!initialized) {
                    showcaseDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "chat-database")
                        .fallbackToDestructiveMigration()
                        .build()
                    initialized = true
                }
                return showcaseDatabase
            }
        }
    }
    abstract fun userDao():UserDao
    abstract fun conversationDao():ConversationDao
    abstract fun messageDao():MessageDao
}


