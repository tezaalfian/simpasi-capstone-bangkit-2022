package com.tezaalfian.simpasi.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "child")
data class ChildEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "tglLahir")
    val tglLahir: String? = null,

    @ColumnInfo(name = "umur")
    val umur: Int? = null,

    @ColumnInfo(name = "tb_bayi")
    val tbBayi: Int? = null,

    @ColumnInfo(name = "bb_bayi")
    val bbBayi: Int? = null,

    @ColumnInfo(name = "alergi")
    val alergi: String? = null,

    @ColumnInfo(name = "user")
    val user: String,

    @ColumnInfo(name = "jk_bayi")
    val jkBayi: String? = null,

    @ColumnInfo(name = "tgl_terdaftar")
    val tglTerdaftar: String? = null
) : Parcelable