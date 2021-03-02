package com.anshul.chatbook

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anshul.repository.ChatBookRepository
import com.anshul.utils.NetworkState
import com.anshul.utils.NetworkStateMonitor

/**
 * @author anshulgoel
 * at 04/09/20, 6:42 PM
 * for ChatBook
 */
class MainActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _authToken = MutableLiveData<String?>()
    val authToken get() = _authToken
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    init {
        _authToken.value = ChatBookRepository(app.applicationContext).userAuthToken
        val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkStateMonitor.registerConnectivityManager(cm)
        _networkState.value = NetworkStateMonitor.value
    }


}