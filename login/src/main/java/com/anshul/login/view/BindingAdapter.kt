package com.anshul.login.view

import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.anshul.login.*

/**
 * @author anshulgoel
 * at 02/09/20, 4:11 PM
 * for ChatBook
 */

@BindingAdapter("passwordValidator")
fun EditText.validatePassword(password: String?) {
    when (passwordValidator(password)) {
        PasswordStates.NULL -> this.error = null
        PasswordStates.VALID_PASSWORD -> this.error = null
        PasswordStates.INVALID_LENGTH -> this.error = context.getString(
            R.string.password_invalid_length,
            MIN_PASSWORD_LENGTH,
            MAX_PASSWORD_LENGTH
        )
        PasswordStates.NO_PASSWORD -> this.error = context.getString(R.string.no_password)
    }
}

@BindingAdapter("emailValidator")
fun EditText.validateEmail(email: String?) {
    when (emailValidator(email)) {
        EmailStates.NULL -> this.error = null
        EmailStates.VALID_EMAIL -> this.error = null
        EmailStates.INVALID_EMAIL -> this.error = context.getString(R.string.invalid_email)
        EmailStates.NO_EMAIL -> this.error = context.getString(R.string.no_email)
    }
}

@BindingAdapter("setButtonState")
fun Button.setButtonState(isEnabled: Boolean) {
    this.isEnabled = isEnabled
    if (isEnabled) {
        this.setTextColor(resources.getColor(R.color.colorAccent))
    } else {
        this.setTextColor(resources.getColor(R.color.disabled))
    }
}