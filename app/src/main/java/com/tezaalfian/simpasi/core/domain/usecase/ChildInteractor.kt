package com.tezaalfian.simpasi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.core.domain.repository.IChildRepository

class ChildInteractor(private val childRepository: IChildRepository): ChildUseCase {

    override fun getChildren(token: String): LiveData<Resource<List<Child>>> = childRepository.getChildren(token)
}