package com.tezaalfian.simpasi.core.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object MyDateFormat {

    fun timeToDate(format: String = "yyyy/MM/dd", time: Long) : String {
        return SimpleDateFormat(format, Locale.US).format(time)
    }

    fun myLocalDateFormat(timestamp: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        val date = sdf.parse(timestamp) as Date
        return DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(date)
    }
}