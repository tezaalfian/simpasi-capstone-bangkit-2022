package com.tezaalfian.simpasi.core.data.source.local.room

import androidx.room.*
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tourism: List<FoodEntity>)

    @Query("SELECT * FROM food WHERE tanggal = :tanggal")
    fun getReceipe(tanggal: String): Flow<List<FoodEntity>>
}