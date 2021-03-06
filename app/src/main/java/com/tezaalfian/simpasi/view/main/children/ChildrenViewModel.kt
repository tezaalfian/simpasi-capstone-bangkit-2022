package com.tezaalfian.simpasi.view.main.children

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.model.Bahan
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class ChildrenViewModel(private val childRepository: ChildRepository, private val userRepository: UserRepository) : ViewModel() {

    fun getChildren(token: String) = childRepository.getChildren(token).asLiveData()

    fun addChild(token: String,
                 nama: String,
                 tglLahir: String,
                 jk_bayi: String,
                 bb_bayi: Int)
    = childRepository.addChild(token, nama, tglLahir, jk_bayi, bb_bayi).asLiveData()

    fun editChild(token: String, child: ChildEntity) = childRepository.editChild(token, child).asLiveData()

    fun bahan(token: String, id: String, bahan: Bahan) = childRepository.bahan(token, id, bahan).asLiveData()

    fun deleteChild(token: String, id: String) = childRepository.deleteChild(token, id).asLiveData()

    fun getToken() : LiveData<String> {
        return userRepository.getToken().asLiveData()
    }
}