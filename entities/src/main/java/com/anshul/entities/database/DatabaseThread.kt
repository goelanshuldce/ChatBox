package com.anshul.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anshul.entities.domain.ChatThread
import com.anshul.entities.formatTime

/**
 * @author anshulgoel
 * at 04/09/20, 9:39 PM
 * for ChatBook
 */

@Entity
data class DatabaseThread(
    @PrimaryKey
    val threadID: Int,
    val count: Int?,
    val id: Double?,
    val userID: Double?,
    val agentID: Double?,
    val body: String?,
    val timeStamp: String?
)
