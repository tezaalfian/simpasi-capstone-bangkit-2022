package com.tezaalfian.simpasi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.domain.model.Child

interface ChildUseCase {
    fun getChildren(token: String): LiveData<Resource<List<Child>>>
}