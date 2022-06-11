package com.tezaalfian.simpasi.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var id: String,
    val resep: String,
    val bahan: String
) : Parcelable