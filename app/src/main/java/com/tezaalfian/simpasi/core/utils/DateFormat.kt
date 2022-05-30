package com.tezaalfian.simpasi.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())
}