package com.anshul.entities.dto

import com.anshul.entities.database.DatabaseMessage
import com.anshul.entities.domain.ChatMessage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author anshulgoel
 * at 04/09/20, 1:27 AM
 * for ChatBook
 */

@JsonClass(generateAdapter = true)
data class NetworkChatMessage(
    @Json(name = "id")
    val id: Double?,

    @Json(name = "thread_id")
    val threadID: Int?,

    @Json(name = "user_id")
    val userID: Double?,

    @Json(name = "agent_id")
    val agentID: Double?,

    @Json(name = "body")
    val body: String?,

    @Json(name = "timestamp")
    val timeStamp: String?
)

fun List<NetworkChatMessage>.asDatabaseModel(): Array<DatabaseMessage> {
    return map {
        DatabaseMessage(
            id = it.id ?: -1.0,
            threadID = it.threadID,
            userID = it.userID,
            agentID = it.agentID,
            body = it.body,
            timeStamp = it.timeStamp
        )
    }.toTypedArray()
}

fun NetworkChatMessage.toDatabaseType(): DatabaseMessage {
    return DatabaseMessage(
        id = id ?: -1.0,
        threadID = threadID ?: -1,
        userID = userID ?: -1.0,
        agentID = agentID ?: -1.0,
        body = body ?: "",
        timeStamp = timeStamp ?: "N/A"
    )
}
