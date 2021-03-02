package com.anshul.entities.domain

/**
 * @author anshulgoel
 * at 04/09/20, 3:34 AM
 * for ChatBook
 */
data class ChatThread(
    val id: Double,
    val userID: String,
    val agentID: String,
    val body: String,
    val timeStamp: String,
    val threadID: String,
    val count: String
)