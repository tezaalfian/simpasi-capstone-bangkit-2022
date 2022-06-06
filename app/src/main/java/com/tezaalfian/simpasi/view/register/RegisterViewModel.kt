package com.tezaalfian.simpasi.view.register

import androidx.lifecycle.ViewModel
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class RegisterViewModel (private val repo: UserRepository) : ViewModel() {
    fun register(nama: String, email: String, username: String, password: String, confirm_password: String)
    = repo.register(nama, email, username, password, confirm_password)
}