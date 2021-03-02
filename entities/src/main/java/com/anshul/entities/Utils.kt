package com.anshul.entities

import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author anshulgoel
 * at 06/09/20, 9:04 PM
 * for ChatBook
 */

fun formatTime(utcDate: String): String {
    try {
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val oldDate = formatter.parse(utcDate)
        val currentDate = Date()
        if (oldDate == null) {
            return "N/A"
        }
        val millis = currentDate.time - oldDate.time
        val builder = StringBuilder()

        when {
            TimeUnit.MILLISECONDS.toDays(millis) > 0 -> {
                builder.append("${TimeUnit.MILLISECONDS.toHours(millis) / 24} days ago")
                return builder.toString()
            }
            TimeUnit.MILLISECONDS.toHours(millis) > 0 -> {
                builder.append("${TimeUnit.MILLISECONDS.toHours(millis)} hours ago")
                return builder.toString()
            }
            TimeUnit.MILLISECONDS.toMinutes(millis) > 0 -> {
                builder.append("${TimeUnit.MILLISECONDS.toMinutes(millis)} mins ago")
                return builder.toString()
            }
            TimeUnit.MILLISECONDS.toSeconds(millis) > 0 -> {
                builder.append("${TimeUnit.MILLISECONDS.toSeconds(millis)} seconds ago")
                return builder.toString()
            }
            else -> return "$millis milli ago"
        }
    }
    catch (e: Exception){
        return "NA"
    }
}