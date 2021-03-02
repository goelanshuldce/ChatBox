package com.anshul.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.anshul.entities.database.FailedMessages

/**
 * @author anshulgoel
 * at 06/09/20, 4:45 PM
 * for ChatBook
 */

@Dao
interface FailedMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: FailedMessages)

    @Query("select * from FailedMessages order by timeStamp ASC LIMIT 1")
    fun getLatestFailedMessage(): LiveData<FailedMessages>

    @Delete
    fun delete(model: FailedMessages)
}

@Database(entities = [FailedMessages::class], version = 1)
abstract class FailedMessageDatabase : RoomDatabase() {
    abstract val failedMessageDao: FailedMessageDao
}

private lateinit var INSTANCE: FailedMessageDatabase

fun getFailedMessagesDatabase(context: Context): FailedMessageDatabase {
    synchronized(FailedMessageDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FailedMessageDatabase::class.java,
                "failedMessages"
            ).build()
        }
    }
    return INSTANCE
}