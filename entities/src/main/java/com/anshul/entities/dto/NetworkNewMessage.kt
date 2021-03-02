package com.anshul.entities.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author anshulgoel
 * at 05/09/20, 5:24 PM
 * for ChatBook
 */

@JsonClass(generateAdapter = true)
data class NetworkNewMessage(
    @Json(name = "thread_id")
    val threadID: Int?,

    @Json(name = "body")
    val body: String?,
)