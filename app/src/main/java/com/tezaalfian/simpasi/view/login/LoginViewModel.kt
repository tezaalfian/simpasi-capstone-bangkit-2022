package com.tezaalfian.simpasi.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel (private val repo: UserRepository) : ViewModel() {

    fun setToken(token: String, id: String, name: String, email: String, username: String){
        viewModelScope.launch {
            repo.setUserData(token, id, name, email, username)
        }
    }

    fun getToken() : LiveData<String> {
        return repo.getToken().asLiveData()
    }

    fun login(usernameEmail: String, password: String) = repo.login(usernameEmail, password).asLiveData()
}