package com.tezaalfian.simpasi.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity

@Dao
interface ChildDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChildren(tourism: List<ChildEntity>)

    @Query("SELECT * FROM child")
    fun getChildren(): LiveData<List<ChildEntity>>

    @Query("DELETE FROM child")
    fun deleteChildren()
}