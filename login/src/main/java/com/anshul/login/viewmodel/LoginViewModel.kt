package com.anshul.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anshul.login.isValidEmail
import com.anshul.login.isValidPassword
import com.anshul.repository.ChatBookRepository
import com.anshul.utils.CombinedLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author anshulgoel
 * at 02/09/20, 3:42 PM
 * for ChatBook
 */

enum class LoginApiStatus { LOADING, ERROR, DONE }


class LoginViewModel(private val app: Application) : AndroidViewModel(app) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val repository = ChatBookRepository(app.applicationContext)

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // State of login request
    private val _loginRequestStatus = MutableLiveData<LoginApiStatus>()

    val showProgressDialog = Transformations.map(_loginRequestStatus) { status ->
        return@map status == LoginApiStatus.LOADING
    }

    val loginSuccess: LiveData<Boolean> = Transformations.map(_loginRequestStatus) {
        it == LoginApiStatus.DONE
    }

    val isValidCredentials = CombinedLiveData(email, password) {
        isValidEmail(it[0] as String?) && isValidPassword(
            it[1] as String?
        )
    }

    fun loginUser() {
        _loginRequestStatus.value = LoginApiStatus.LOADING
        coroutineScope.launch {
            try {
                repository.requestAuthToken(
                    email.value ?: "",
                    password.value ?: ""
                )
                _loginRequestStatus.value = LoginApiStatus.DONE
            } catch (e: Exception) {
                _loginRequestStatus.value = LoginApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}