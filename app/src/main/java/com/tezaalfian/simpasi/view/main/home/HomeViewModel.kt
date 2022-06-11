package com.tezaalfian.simpasi.view.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.FoodRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

class HomeViewModel(private val foodRepository: FoodRepository, private val userRepository: UserRepository) : ViewModel() {

    fun getLastFood(token: String) = foodRepository.getLasFood(token).asLiveData()

    fun getToken() = userRepository.getToken().asLiveData()
}