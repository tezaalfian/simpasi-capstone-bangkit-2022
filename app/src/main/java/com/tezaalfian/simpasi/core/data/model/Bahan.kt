package com.tezaalfian.simpasi.core.data.model

data class Bahan(
    val Roti_Tawar: String,
    val Cumi_Cumi: String,
    val Tepung_Beras: String,
    val Pisang: String,
    val Telur_Bebek: String,
    val Kacang_Tanah: String,
    val Kerang: String,
    val Alpukat: String
)

data class Feedback(
    val alergi: Bahan
)
