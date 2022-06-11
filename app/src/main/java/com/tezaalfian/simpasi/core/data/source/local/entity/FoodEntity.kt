package com.tezaalfian.simpasi.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "food_id")
    var foodId: String,

    @ColumnInfo(name = "resep")
    val resep: String,

    @ColumnInfo(name = "bahan")
    val bahan: String,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null
)