package com.tezaalfian.simpasi.core.utils

import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun TextView.setLocalDateFormat(timestamp: String) {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = sdf.parse(timestamp) as Date

    val formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date)
    this.text = formattedDate
}