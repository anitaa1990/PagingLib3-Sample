package com.an.paginglib3_sample.ext

import java.text.SimpleDateFormat
import java.util.*

fun String.getDate(): String {
    val currentDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())
    val date = currentDateFormat.parse(this)
    return date?.let {
        val newDateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        newDateFormat.format(date)
    } ?: ""
}

fun String.getTime(): String {
    val currentDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())
    val date = currentDateFormat.parse(this)
    return date?.let {
        val newDateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        newDateFormat.format(date)
    } ?: ""
}
