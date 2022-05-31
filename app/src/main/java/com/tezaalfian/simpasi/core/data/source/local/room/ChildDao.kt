package com.tezaalfian.simpasi.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity

@Dao
interface ChildDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(tourism: List<ChildEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(tourism: ChildEntity)

    @Query("SELECT * FROM child")
    fun getChildren(): LiveData<List<ChildEntity>>

    @Query("DELETE FROM child")
    suspend fun deleteAll()

    @Update
    suspend fun updateChild(child: ChildEntity): Int
}