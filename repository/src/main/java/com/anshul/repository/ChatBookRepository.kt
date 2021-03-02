package com.anshul.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.anshul.network.Api
import com.anshul.database.ChatMessagesDatabase
import com.anshul.database.PreferenceHelper
import com.anshul.database.PreferenceHelper.authToken
import com.anshul.database.getChatMessagesDatabase
import com.anshul.entities.domain.ChatThread
import com.anshul.entities.dto.asDatabaseModel
import com.anshul.entities.formatTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author anshulgoel
 * at 04/09/20, 3:04 AM
 * for ChatBook
 */
class ChatBookRepository(private val context: Context) {

    private val database: ChatMessagesDatabase = getChatMessagesDatabase(context)

    val userAuthToken = PreferenceHelper.customPreference(context).authToken

    suspend fun requestAuthToken(email: String, password: String) {
        withContext(Dispatchers.IO) {
            PreferenceHelper.customPreference(context).authToken =
                Api.apiService.loginAsync(email, password).await().authToken
        }
    }

    val threads: LiveData<List<ChatThread>> =
        Transformations.map(database.messagesDao.getAllThreads()) { list ->
            list.map {
                ChatThread(
                    it.id ?: -1.0,
                    it.userID?.toString() ?: "",
                    it.agentID?.toString() ?: "",
                    it.body ?: "",
                    formatTime(it.timeStamp ?: ""),
                    it.threadID.toString(),
                    it.count?.toString() ?: ""
                )
            }.reversed()
        }


    suspend fun requestAllChatMessages() {
        withContext(Dispatchers.IO) {
            try {
                if (userAuthToken.isNullOrEmpty()) {
                    return@withContext
                }
                val messages = Api.apiService.getMessagesAsync(userAuthToken).await()
                database.messagesDao.insertAllMessages(*messages.asDatabaseModel())
            } catch (e: Exception) {

            }
        }
    }
}