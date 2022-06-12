package com.tezaalfian.simpasi.view.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repo: UserRepository) : ViewModel() {
    fun getUser() = repo.getUser().asLiveData()

    fun getToken() = repo.getToken().asLiveData()

    fun editProfile (token: String, id: String, nama: String, email: String, username: String) =
            repo.editProfile(token, id, nama, email, username).asLiveData()

    fun setProfile (token: String, id: String, nama: String, email: String, username: String) {
        viewModelScope.launch {
            repo.setUserData(token, id, nama, email, username)
        }
    }

    fun changePassword (token: String, id: String, password: String, confirm_password: String) =
            repo.changePassword(token, id, password, confirm_password).asLiveData()


    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}