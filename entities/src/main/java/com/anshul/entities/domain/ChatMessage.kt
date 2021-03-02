package com.anshul.entities.domain

/**
 * @author anshulgoel
 * at 04/09/20, 3:35 AM
 * for ChatBook
 */
data class ChatMessage(
    val id: String,
    val userID: String,
    val agentID: String,
    val body: String,
    val timeStamp: String
)