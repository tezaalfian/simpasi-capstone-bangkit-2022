package com.tezaalfian.simpasi.view.main.food

import android.util.Log
import androidx.lifecycle.*
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun getFood(tanggal: String) = foodRepository.getFood(tanggal).asLiveData()

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