package com.tezaalfian.simpasi.view.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class ProfileViewModel (private val repo: UserRepository) : ViewModel() {
    fun getUser() = repo.getUser().asLiveData()
}