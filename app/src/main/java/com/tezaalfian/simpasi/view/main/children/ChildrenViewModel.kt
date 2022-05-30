package com.tezaalfian.simpasi.view.main.children

import androidx.lifecycle.ViewModel
import com.tezaalfian.simpasi.core.domain.usecase.ChildUseCase

class ChildrenViewModel(private val childUseCase: ChildUseCase) : ViewModel() {

    fun getChildren(token: String) = childUseCase.getChildren(token)
}