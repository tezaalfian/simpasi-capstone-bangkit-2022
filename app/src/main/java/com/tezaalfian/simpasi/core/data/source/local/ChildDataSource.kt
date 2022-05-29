package com.tezaalfian.simpasi.core.data.source.local

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.local.room.ChildDao

class ChildDataSource private constructor(private val childDao: ChildDao){

    fun getChildren(): LiveData<List<ChildEntity>> = childDao.getChildren()

    fun insertChildren(childList: List<ChildEntity>) = childDao.insertChildren(childList)

    companion object {
        private var instance: ChildDataSource? = null

        fun getInstance(childDao: ChildDao): ChildDataSource =
            instance ?: synchronized(this) {
                instance ?: ChildDataSource(childDao)
            }
    }
}