package com.anshul.chatbook.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.anshul.repository.ChatBookRepository
import com.anshul.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author anshulgoel
 * at 05/09/20, 2:35 PM
 * for ChatBook
 */
class SyncService : Service() {

    private lateinit var mHandler: Handler
    private var serviceJob = Job()
    private val coroutineScope = CoroutineScope(serviceJob + Dispatchers.IO)


    // default interval for syncing data
    val DEFAULT_SYNC_INTERVAL: Long = 5 * 1000.toLong()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mHandler = Handler()
        mHandler.post(runnableService)
        return START_STICKY
    }

    override fun onDestroy() {
        if (::mHandler.isInitialized) {
            mHandler.removeCallbacks(runnableService)
        }
        stopSelf()
        super.onDestroy()
        serviceJob.cancel()
    }

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            syncData()
            syncFailedData()
            mHandler.postDelayed(this, DEFAULT_SYNC_INTERVAL)
        }
    }

    @Synchronized
    private fun syncData() {
        val chatBookRepository = ChatBookRepository(applicationContext)
        coroutineScope.launch {
            chatBookRepository.requestAllChatMessages()
        }
    }

    @Synchronized
    private fun syncFailedData() {
        val messageRepository = MessageRepository(applicationContext, -1)
        coroutineScope.launch {
            messageRepository.retryFailedMessage()
        }
    }
}