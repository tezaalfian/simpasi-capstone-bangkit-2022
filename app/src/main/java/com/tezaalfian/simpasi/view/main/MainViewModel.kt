package com.tezaalfian.simpasi.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tezaalfian.simpasi.core.data.model.User
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepo: UserRepository) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

//    fun logout() {
//        viewModelScope.launch {
//            userRepo.logout()
//        }
//    }
}