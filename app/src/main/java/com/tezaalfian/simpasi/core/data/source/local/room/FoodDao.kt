package com.tezaalfian.simpasi.core.data.source.local.room

import androidx.room.*
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity)

    @Query("SELECT * FROM food WHERE tanggal = :tanggal AND token = :token")
    fun getReceipe(tanggal: String, token: String): Flow<List<FoodEntity>>

    @Delete
    suspend fun delete(food: FoodEntity)

    @Query("SELECT id FROM food WHERE token = :token ORDER BY id DESC LIMIT 2")
    fun getLastFood(token: String): Flow<List<Int>>
}