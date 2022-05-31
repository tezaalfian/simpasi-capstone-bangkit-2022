package com.tezaalfian.simpasi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.domain.model.Child

interface ChildUseCase {
    fun getChildren(token: String): LiveData<Resource<List<Child>>>
    fun addChild(
        token: String,
        nama: String,
        tglLahir: String,
        jk_bayi: String,
        tb_bayi: Int,
        bb_bayi: Int,
        alergi: String?
    ): LiveData<Resource<Child>>
}