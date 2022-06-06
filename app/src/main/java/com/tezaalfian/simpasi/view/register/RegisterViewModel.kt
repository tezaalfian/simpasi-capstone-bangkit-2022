package com.tezaalfian.simpasi.view.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class RegisterViewModel (private val repo: UserRepository) : ViewModel() {
    fun register(name: String, email: String, username: String, password: String, confirm_password: String)
    = repo.register(name, email, username, password, confirm_password).asLiveData()
}