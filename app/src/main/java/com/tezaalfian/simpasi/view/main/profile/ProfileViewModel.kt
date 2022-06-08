package com.tezaalfian.simpasi.view.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repo: UserRepository) : ViewModel() {
    fun getUser() = repo.getUser().asLiveData()

    fun getToken() = repo.getToken().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}