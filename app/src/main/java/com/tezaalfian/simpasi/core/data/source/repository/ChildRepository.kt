package com.tezaalfian.simpasi.core.data.source.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.NetworkBoundResource
import com.tezaalfian.simpasi.core.data.source.local.ChildDataSource as LocalDataSource
import com.tezaalfian.simpasi.core.data.source.remote.RemoteDataSource
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.core.domain.repository.IChildRepository
import com.tezaalfian.simpasi.core.utils.AppExecutors
import com.tezaalfian.simpasi.core.utils.DataMapper

class ChildRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val childDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IChildRepository{
    override fun getChildren(token: String): LiveData<Resource<List<Child>>> =
        object : NetworkBoundResource<List<Child>, List<ChildResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Child>> {
                return Transformations.map(childDataSource.getChildren()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Child>?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<List<ChildResponse>>> =
                remoteDataSource.getChildren(token)

            override fun saveCallResult(data: List<ChildResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                childDataSource.insertChildren(tourismList)
            }
        }.asLiveData()

    override fun addChild(
        token: String,
        nama: String,
        tglLahir: String,
        jk_bayi: String,
        tb_bayi: Int,
        bb_bayi: Int,
        alergi: String?
    ): LiveData<Resource<Child>> {
        val result = MediatorLiveData<Resource<Child>>()
        val apiResponse = remoteDataSource.addChild(token, nama, tglLahir, jk_bayi, tb_bayi, bb_bayi, alergi)
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    appExecutors.mainThread().execute {
                        try {
                            result.value = Resource.Success(DataMapper.mapResponseToDomain(response.data))
                        }catch (e: Exception){
                            result.value = Resource.Error(e.message.toString())
                        }
                    }
                }
                is ApiResponse.Error -> {
                    appExecutors.mainThread().execute {
                        result.value = Resource.Error(response.errorMessage)
                    }
                }
                is ApiResponse.Empty -> {

                }
            }
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: ChildRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): ChildRepository =
            instance ?: synchronized(this) {
                instance ?: ChildRepository(remoteData, localData, appExecutors)
            }
    }
}