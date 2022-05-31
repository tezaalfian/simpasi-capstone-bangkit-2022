package com.tezaalfian.simpasi.view.main.children

import androidx.lifecycle.ViewModel
import com.tezaalfian.simpasi.core.domain.usecase.ChildUseCase

class ChildrenViewModel(private val childUseCase: ChildUseCase) : ViewModel() {

    fun getChildren(token: String) = childUseCase.getChildren(token)

    fun addChild(token: String,
                 nama: String,
                 tglLahir: String,
                 jk_bayi: String,
                 tb_bayi: Int,
                 bb_bayi: Int,
                 alergi: String?)
    = childUseCase.addChild(token, nama, tglLahir, jk_bayi, tb_bayi, bb_bayi,alergi)
}