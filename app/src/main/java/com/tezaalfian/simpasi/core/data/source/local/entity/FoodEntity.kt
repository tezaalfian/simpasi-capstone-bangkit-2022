package com.tezaalfian.simpasi.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food")
data class FoodEntity(
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "resep")
    val resep: String,

    @ColumnInfo(name = "bahan")
    val bahan: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String? = null
) : Parcelable