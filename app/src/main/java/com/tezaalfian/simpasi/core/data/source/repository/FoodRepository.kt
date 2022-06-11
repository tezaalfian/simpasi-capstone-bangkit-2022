package com.tezaalfian.simpasi.core.data.source.repository

import android.util.Log
import com.google.gson.Gson
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.Bahan
import com.tezaalfian.simpasi.core.data.model.Feedback
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.data.source.local.room.ChildDao
import com.tezaalfian.simpasi.core.data.source.local.room.FoodDao
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.DeleteChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.ErrorResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.UpdateChildResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
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