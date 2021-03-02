package com.anshul.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.anshul.network.Api
import com.anshul.database.*
import com.anshul.database.PreferenceHelper.authToken
import com.anshul.entities.database.DatabaseMessage
import com.anshul.entities.database.FailedMessages
import com.anshul.entities.database.toDomainType
import com.anshul.entities.domain.ChatMessage
import com.anshul.entities.dto.toDatabaseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @author anshulgoel
 * at 04/09/20, 11:24 PM
 * for ChatBook
 */
class MessageRepository(context: Context, threadID: Int) {
    private val databaseChatMessage: ChatMessagesDatabase = getChatMessagesDatabase(context)
    private val databaseFailedMessage: FailedMessageDatabase = getFailedMessagesDatabase(context)

    private val userAuthToken = PreferenceHelper.customPreference(context).authToken

    val messages: LiveData<List<ChatMessage>> =
        Transformations.map(databaseChatMessage.messagesDao.getAllMessagesForThread(threadID)) {
            it.toDomainType()
        }

    suspend fun retryFailedMessage() {
        withContext(Dispatchers.IO) {
            val failedMessage: FailedMessages? =
                databaseFailedMessage.failedMessageDao.getLatestFailedMessage().value
            failedMessage?.let {
                databaseFailedMessage.failedMessageDao.delete(it)
                sendMessage(it.threadID.toInt(), it.message)
            }
        }
    }

    // Send the new message and add it to db as per fail or success
    suspend fun sendMessage(threadID: Int, body: String) {
        withContext(Dispatchers.IO) {
            try {
                if (userAuthToken.isNullOrEmpty()) {
                    return@withContext
                }
                val message: DatabaseMessage =
                    Api.apiService.sendMessageAsync(userAuthToken, threadID, body).await()
                        .toDatabaseType()
                databaseChatMessage.messagesDao.insertMessage(message)
            } catch (e: Exception) {
                databaseFailedMessage.failedMessageDao.insertMessage(
                    FailedMessages(
                        0,
                        threadID.toString(),
                        body,
                        Date().toString()
                    )
                )
                throw e
            }
        }
    }
}