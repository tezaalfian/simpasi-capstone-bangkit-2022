package com.tezaalfian.simpasi.core.data.source.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.local.room.ChildDao
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.DeleteChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.ErrorResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.UpdateChildResponse
import okhttp3.internal.readMedium
import retrofit2.HttpException

class ChildRepository private constructor(
    private val apiService: ApiService,
    private val childDao: ChildDao
) {
    fun getChildren(token: String): LiveData<Resource<List<ChildEntity>>> = liveData {
        emit(Resource.Loading)
        try {
            val client = apiService.getChildren(token)
            childDao.deleteAll()
            childDao.insertChildren(client.map {
                ChildEntity(
                    it.id, it.nama, it.tglLahir, it.bbBayi, it.alergi, it.user, it.jkBayi, it.tglTerdaftar
                )
            })
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
        try {
            val localData : LiveData<Resource<List<ChildEntity>>> = childDao.getChildren().map { Resource.Success(it) }
            emitSource(localData)
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun deleteChild(token: String, id: String): LiveData<Resource<DeleteChildResponse>> = liveData {
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
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Resource.Error(it.toString()))
                }
            } catch (exception: java.lang.Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun editChild(
        token: String,
        child: ChildEntity,
    ): LiveData<Resource<UpdateChildResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val client = apiService.editChild(token, child.id, child.nama, child.tglLahir, child.jkBayi, child.bbBayi, child.alergi)
            try {
                childDao.updateChild(child)
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Resource.Error(it.toString()))
                }
            } catch (exception: java.lang.Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun addChild(
        token: String,
        nama: String,
        tglLahir: String,
        jk_bayi: String,
        bb_bayi: Int,
        alergi: String?
    ): LiveData<Resource<ChildResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val client = apiService.addChild(token, nama, tglLahir, jk_bayi, bb_bayi, alergi)
            try {
                childDao.insertChild(
                    ChildEntity(
                        client.id, client.nama, client.tglLahir, client.bbBayi, client.alergi, client.user, client.jkBayi, client.tglTerdaftar
                    )
                )
            }catch (e: Exception){

            }
            emit(Resource.Success(client))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Resource.Error(it.toString()))
                }
            } catch (exception: java.lang.Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }

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