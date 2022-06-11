package com.tezaalfian.simpasi.core.data.source.repository

import android.util.Log
import com.google.gson.Gson
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.Bahan
import com.tezaalfian.simpasi.core.data.model.Feedback
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.local.room.ChildDao
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.DeleteChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.ErrorResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.UpdateChildResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

class ChildRepository private constructor(
    private val apiService: ApiService,
    private val childDao: ChildDao
) {
    fun getChildren(token: String): Flow<Resource<List<ChildEntity>>> = flow {
        emit(Resource.Loading)
        try {
            val client = apiService.getChildren(token)
            childDao.deleteAll()
            childDao.insertChildren(client.map {
                ChildEntity(
                    it.id, it.nama, it.tglLahir, it.bbBayi, it.user, it.jkBayi, it.tglTerdaftar
                )
            })
        }catch (e: HttpException){
            try {
                val errorResponse = Gson().fromJson(e.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
        try {
            val localData : Flow<Resource<List<ChildEntity>>> = childDao.getChildren().map { Resource.Success(it) }
            emitAll(localData)
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun deleteChild(token: String, id: String): Flow<Resource<DeleteChildResponse>> = flow {
        emit(Resource.Loading)
        try {
            val client = apiService.deleteChild(token, id)
            try {
                childDao.delete(id)
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun editChild(
        token: String,
        child: ChildEntity,
    ): Flow<Resource<UpdateChildResponse>> = flow {
        emit(Resource.Loading)
        try {
            val client = apiService.editChild(token, child.id, child.nama, child.tglLahir, child.jkBayi, child.bbBayi)
            try {
                childDao.updateChild(child)
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun bahan(
        token: String,
        id: String,
        bahan: Bahan
    ): Flow<Resource<UpdateChildResponse>> = flow {
        emit(Resource.Loading)
        try {
            val client = apiService.bahan(token, id, Feedback(bahan))
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun addChild(
        token: String,
        nama: String,
        tglLahir: String,
        jk_bayi: String,
        bb_bayi: Int
    ): Flow<Resource<ChildResponse>> = flow {
        emit(Resource.Loading)
        try {
            val client = apiService.addChild(token, nama, tglLahir, jk_bayi, bb_bayi)
            try {
                childDao.insertChild(
                    ChildEntity(
                        client.id, client.nama, client.tglLahir, client.bbBayi, client.user, client.jkBayi, client.tglTerdaftar
                    )
                )
            }catch (e: Exception){

            }
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: ChildRepository? = null

        fun getInstance(
            apiService: ApiService,
            childDao: ChildDao
        ): ChildRepository =
            instance ?: synchronized(this) {
                instance ?: ChildRepository(apiService, childDao)
            }
    }
}