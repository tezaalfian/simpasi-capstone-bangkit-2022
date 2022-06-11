package com.tezaalfian.simpasi.view.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tezaalfian.simpasi.core.data.source.repository.FoodRepository

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun getLastFood() = foodRepository.getLasFood().asLiveData()
}