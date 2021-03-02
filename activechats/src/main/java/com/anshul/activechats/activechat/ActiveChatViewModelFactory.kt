package com.anshul.activechats.activechat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author anshulgoel
 * at 06/09/20, 3:12 AM
 * for ChatBook
 */
class ActiveChatViewModelFactory(
    private val application: Application, private val threadID: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActiveChatFragmentViewModel::class.java)) {
            return ActiveChatFragmentViewModel(application, threadID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}