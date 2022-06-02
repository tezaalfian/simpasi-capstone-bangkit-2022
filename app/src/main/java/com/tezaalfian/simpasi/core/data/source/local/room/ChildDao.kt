package com.tezaalfian.simpasi.core.data.source.local.room

import androidx.room.*
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(tourism: List<ChildEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(tourism: ChildEntity)

    @Query("SELECT * FROM child")
    fun getChildren(): Flow<List<ChildEntity>>

    @Query("DELETE FROM child")
    suspend fun deleteAll()

    @Query("DELETE FROM child WHERE id = :id")
    suspend fun delete(id: String)

    @Update
    suspend fun updateChild(child: ChildEntity): Int
}