package com.anshul.activechats.thread

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anshul.entities.domain.ChatThread
import com.anshul.repository.ChatBookRepository
import kotlinx.coroutines.launch

/**
 * @author anshulgoel
 * at 04/09/20, 8:37 PM
 * for ChatBook
 */
class ThreadFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val chatBookRepository = ChatBookRepository(application.applicationContext)
    val threads = chatBookRepository.threads
    val authToken = chatBookRepository.userAuthToken

    init {
        viewModelScope.launch {
            chatBookRepository.requestAllChatMessages()
        }
    }

    // Internally, we use a MutableLiveData to handle navigation to the selected thread id
    private val _navigateToSelectedThread = MutableLiveData<Int>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedThread: LiveData<Int>
        get() = _navigateToSelectedThread

    /**
     * When the property is clicked, set the [_navigateToSelectedThread] [MutableLiveData]
     * @param thread The [ChatThread] that was clicked on.
     */
    fun displayThreadDetails(thread: Int) {
        _navigateToSelectedThread.value = thread
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayThreadDetailsComplete() {
        _navigateToSelectedThread.value = null
    }


}