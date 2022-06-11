package com.tezaalfian.simpasi.core.data.source.repository

import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.data.source.local.room.FoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception

class FoodRepository private constructor(
    private val foodDao: FoodDao
) {

    fun getFood(tanggal: String) : Flow<Resource<List<FoodEntity>>> = flow {
        emit(Resource.Loading)
        try {
            val localData : Flow<Resource<List<FoodEntity>>> = foodDao.getReceipe(tanggal).map { Resource.Success(it) }
            emitAll(localData)
        }catch (exception: Exception) {
            emit(Resource.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getLasFood() : Flow<Resource<List<Int>>> = flow {
        emit(Resource.Loading)
        try {
            val localData : Flow<Resource<List<Int>>> = foodDao.getLastFood().map { Resource.Success(it) }
            emitAll(localData)
        }catch (exception: Exception) {
            emit(Resource.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun insertFood(food: FoodEntity): String {
        return try {
            foodDao.insert(food)
            "success"
        }catch (e: Exception){
            e.message.toString()
        }
    }

    suspend fun deleteFood(food: FoodEntity){
        foodDao.delete(food)
    }

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(
            foodDao: FoodDao
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(foodDao)
            }
    }
}