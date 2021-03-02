package com.anshul.entities.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author anshulgoel
 * at 02/09/20, 11:24 PM
 * for ChatBook
 */

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "auth_token")
    val authToken: String
)