package com.anshul.login

import androidx.core.util.PatternsCompat


/**
 * @author anshulgoel
 * at 02/09/20, 5:26 PM
 * for ChatBook
 */


const val MIN_PASSWORD_LENGTH = 4
const val MAX_PASSWORD_LENGTH = 40
private const val LOGIN_TOKEN_ALIAS = "login_token_alias"
private const val TAG = "LoginUtil"

enum class EmailStates {
    NULL,
    NO_EMAIL,
    INVALID_EMAIL,
    VALID_EMAIL
}

enum class PasswordStates {
    NULL,
    NO_PASSWORD,
    INVALID_LENGTH,
    VALID_PASSWORD
}

fun emailValidator(email: String?): EmailStates {
    if (email == null) {
        return EmailStates.NULL
    }
    if (email.isEmpty()) {
        return EmailStates.NO_EMAIL
    }
    if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
        return EmailStates.INVALID_EMAIL
    }
    return EmailStates.VALID_EMAIL
}

fun passwordValidator(password: String?): PasswordStates {
    if (password == null) {
        return PasswordStates.NULL
    }
    if (password.isEmpty()) {
        return PasswordStates.NO_PASSWORD
    }
    if (password.length > MAX_PASSWORD_LENGTH || password.length < MIN_PASSWORD_LENGTH) {
        return PasswordStates.INVALID_LENGTH
    }
    return PasswordStates.VALID_PASSWORD
}

fun isValidEmail(email: String?): Boolean {
    return emailValidator(email) == EmailStates.VALID_EMAIL
}

fun isValidPassword(password: String?): Boolean {
    return passwordValidator(password) == PasswordStates.VALID_PASSWORD
}

