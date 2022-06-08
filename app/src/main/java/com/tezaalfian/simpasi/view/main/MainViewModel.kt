package com.tezaalfian.simpasi.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

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