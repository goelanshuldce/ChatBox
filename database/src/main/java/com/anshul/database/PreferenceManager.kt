package com.anshul.database

import android.content.Context
import android.content.SharedPreferences

/**
 * @author anshulgoel
 * at 03/09/20, 7:08 PM
 * for ChatBook
 */

object PreferenceHelper {

    private const val AUTH_TOKEN = "AUTH_TOKEN"
    private const val customPreferenceName = "chat_book_shared_pref"


    fun customPreference(context: Context): SharedPreferences = context.getSharedPreferences(
        customPreferenceName, Context.MODE_PRIVATE
    )

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.authToken: String?
        get() {
            return getString(AUTH_TOKEN, "")
        }
        set(value) {
            editMe {
                it.putString(AUTH_TOKEN, value)
            }
        }
}