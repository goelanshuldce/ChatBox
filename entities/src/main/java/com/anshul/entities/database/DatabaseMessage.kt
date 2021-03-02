package com.anshul.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anshul.entities.domain.ChatMessage
import com.anshul.entities.formatTime

/**
 * @author anshulgoel
 * at 04/09/20, 2:01 AM
 * for ChatBook
 */

@Entity
data class DatabaseMessage(
    @PrimaryKey
    val id: Double,
    val threadID: Int?,
    val userID: Double?,
    val agentID: Double?,
    val body: String?,
    val timeStamp: String?
)

fun List<DatabaseMessage>.toDomainType(): List<ChatMessage> {
    return map {
        ChatMessage(
            id = it.id.toString() ?: "",
            userID = it.userID?.toString() ?: "",
            agentID = it.agentID?.toString() ?: "",
            body = it.body ?: "",
            timeStamp = formatTime(it.timeStamp ?: ""),
        )
    }
}