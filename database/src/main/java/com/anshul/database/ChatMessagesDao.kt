package com.anshul.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.anshul.entities.database.DatabaseMessage
import com.anshul.entities.database.DatabaseThread

/**
 * @author anshulgoel
 * at 04/09/20, 2:09 AM
 * for ChatBook
 */

@Dao
interface ChatMessageDao {

    @Query("select threadID, COUNT(id) AS count, id, userID, agentID, timeStamp, body  from DatabaseMessage GROUP BY threadID ORDER BY timeStamp DESC")
    fun getAllThreads(): LiveData<List<DatabaseThread>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMessages(vararg message: DatabaseMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: DatabaseMessage)

    @Query("select * from DatabaseMessage where threadID = :threadId ORDER BY timeStamp ASC")
    fun getAllMessagesForThread(threadId: Int): LiveData<List<DatabaseMessage>>

    @Query("select * from DatabaseMessage")
    fun getAllMessages(): LiveData<List<DatabaseMessage>>

    @Query("select * from DatabaseMessage where threadID = :threadId order by timeStamp DESC LIMIT 1")
    fun getLatestMessageForThread(threadId: Int): LiveData<DatabaseMessage>
}

@Database(entities = [DatabaseMessage::class], version = 1)
abstract class ChatMessagesDatabase : RoomDatabase() {
    abstract val messagesDao: ChatMessageDao
}

private lateinit var INSTANCE: ChatMessagesDatabase

fun getChatMessagesDatabase(context: Context): ChatMessagesDatabase {
    synchronized(ChatMessagesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ChatMessagesDatabase::class.java,
                "chatmessages"
            ).build()
        }
    }
    return INSTANCE
}


