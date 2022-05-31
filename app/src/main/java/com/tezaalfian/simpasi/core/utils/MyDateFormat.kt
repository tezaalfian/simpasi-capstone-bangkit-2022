package com.tezaalfian.simpasi.core.utils

import java.text.SimpleDateFormat
import java.util.*

object MyDateFormat {
    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun timeToDate(format: String = "yyyy/MM/dd", time: Long) : String {
        return SimpleDateFormat(format, Locale.US).format(time)
    }

    const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjkwOGYxMzQwOWYxNGY1NzZjZTgyNGQiLCJpYXQiOjE2NTM3MzA3ODV9.lMzYrfJWqmzk3xKlS2fde_wWeS16jvglmvaeu2qhyJ0"
}