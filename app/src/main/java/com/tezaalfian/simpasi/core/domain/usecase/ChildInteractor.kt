package com.tezaalfian.simpasi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.core.domain.repository.IChildRepository

class ChildInteractor(private val childRepository: IChildRepository): ChildUseCase {

    override fun getChildren(token: String): LiveData<Resource<List<Child>>> = childRepository.getChildren(token)
    override fun addChild(
        token: String,
        nama: String,
        tglLahir: String,
        jk_bayi: String,
        tb_bayi: Int,
        bb_bayi: Int,
        alergi: String?
    ): LiveData<Resource<Child>> = childRepository.addChild(token, nama, tglLahir, jk_bayi, tb_bayi, bb_bayi, alergi)
}