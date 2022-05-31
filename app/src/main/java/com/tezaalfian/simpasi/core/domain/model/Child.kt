package com.tezaalfian.simpasi.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Child(
    val id: String,
    val nama: String,
    val tglLahir: String,
    val umur: Int? = null,
    val tbBayi: Int,
    val bbBayi: Int,
    val alergi: String? = null,
    val user: String,
    val jkBayi: String,
    val tglTerdaftar: String? = null
):Parcelable
