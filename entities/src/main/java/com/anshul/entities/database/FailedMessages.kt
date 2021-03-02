package com.anshul.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anshul.entities.dto.NetworkNewMessage
import java.util.*

/**
 * @author anshulgoel
 * at 06/09/20, 4:38 PM
 * for ChatBook
 */

@Entity
data class FailedMessages(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val threadID: String,
    val message: String,
    val timeStamp: String = Date().toString()
)