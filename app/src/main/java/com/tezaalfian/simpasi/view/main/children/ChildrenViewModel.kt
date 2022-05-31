package com.tezaalfian.simpasi.view.main.children

import androidx.lifecycle.ViewModel
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository

class ChildrenViewModel(private val childRepository: ChildRepository) : ViewModel() {

    fun getChildren(token: String) = childRepository.getChildren(token)

    fun addChild(token: String,
                 nama: String,
                 tglLahir: String,
                 jk_bayi: String,
                 bb_bayi: Int,
                 alergi: String?)
    = childRepository.addChild(token, nama, tglLahir, jk_bayi, bb_bayi,alergi)

    fun editChild(token: String, child: ChildEntity) = childRepository.editChild(token, child)

    fun deleteChild(token: String, id: String) = childRepository.deleteChild(token, id)
}