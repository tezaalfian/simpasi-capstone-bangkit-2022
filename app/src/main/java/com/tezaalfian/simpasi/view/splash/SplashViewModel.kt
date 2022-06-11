package com.tezaalfian.simpasi.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class SplashViewModel constructor(private val userRepository: UserRepository) : ViewModel() {

    fun getAuthToken() = userRepository.getToken().asLiveData()
}