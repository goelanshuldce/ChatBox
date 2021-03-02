package com.anshul.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

/**
 * @author anshulgoel
 * at 06/09/20, 7:49 PM
 * for ChatBook
 */

enum class NetworkState {
    CONNECTION_LOST,
    CONNECTED,
    DISCONNECTED,
    CONNECTING
}

object NetworkStateMonitor :
    LiveData<NetworkState>() {

    private lateinit var cm: ConnectivityManager

    private var hasNetworkChanged: Boolean = false

    private val networkStateObject = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            hasNetworkChanged = true
            postValue(NetworkState.CONNECTION_LOST)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            hasNetworkChanged = true
            postValue(NetworkState.DISCONNECTED)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (hasNetworkChanged) {
                postValue(NetworkState.CONNECTED)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        if (!::cm.isInitialized) {
            return
        }
        cm.unregisterNetworkCallback(networkStateObject)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        super.onActive()
        if (!::cm.isInitialized) {
            return
        }
        cm.registerNetworkCallback(networkRequestBuilder(), networkStateObject)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun networkRequestBuilder(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    fun registerConnectivityManager(input: ConnectivityManager) {
        cm = input
    }
}