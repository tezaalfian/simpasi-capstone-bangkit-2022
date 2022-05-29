package com.tezaalfian.simpasi.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Child(
    val id: String,
    val nama: String,
    val tglLahir: String? = null,
    val umur: Int? = null,
    val tbBayi: Int? = null,
    val bbBayi: Int? = null,
    val alergi: String? = null,
    val user: String,
    val jkBayi: String? = null,
    val tglTerdaftar: String? = null
):Parcelable
