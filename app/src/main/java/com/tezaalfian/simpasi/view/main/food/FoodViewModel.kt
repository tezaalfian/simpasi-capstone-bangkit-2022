package com.tezaalfian.simpasi.view.main.food

import androidx.lifecycle.*
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.data.source.repository.FoodRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository, private val userRepository: UserRepository) : ViewModel() {

    fun getFood(tanggal: String, token: String) = foodRepository.getFood(tanggal, token).asLiveData()

    fun getToken() = userRepository.getToken().asLiveData()

    fun insertFood(food: FoodEntity) {
        viewModelScope.launch {
            foodRepository.insertFood(food)
        }
    }

    fun deleteFood(food: FoodEntity){
        viewModelScope.launch {
            foodRepository.deleteFood(food)
        }
    }
}