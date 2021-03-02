package com.anshul.activechats.activechat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshul.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author anshulgoel
 * at 06/09/20, 1:21 AM
 * for ChatBook
 */

class ActiveChatFragmentViewModel(application: Application, private val threadID: Int) :
    AndroidViewModel(application) {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var message: String = ""

    private val messageRepository = MessageRepository(application.applicationContext, threadID)
    val messages = messageRepository.messages

    private val _buttonClicked = MutableLiveData<Boolean>()
    val buttonClicked: LiveData<Boolean> get() = _buttonClicked

    init {
        _buttonClicked.value = false
    }

    fun sendMessage() {
        coroutineScope.launch {
            try {
                messageRepository.sendMessage(
                    threadID,
                    message
                )
                _buttonClicked.value = true
            } catch (e: Exception) {
            }
        }
    }

    fun buttonClickDone() {
        _buttonClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}