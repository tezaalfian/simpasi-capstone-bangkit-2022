package com.tezaalfian.simpasi.core.domain.repository

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.domain.model.Child

interface IChildRepository {

    fun getChildren(token: String): LiveData<Resource<List<Child>>>
}