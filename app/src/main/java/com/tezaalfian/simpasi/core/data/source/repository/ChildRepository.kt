package com.tezaalfian.simpasi.core.data.source.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.NetworkBoundResource
import com.tezaalfian.simpasi.core.data.source.remote.RemoteDataSource
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.core.domain.repository.IChildRepository
import com.tezaalfian.simpasi.core.utils.AppExecutors

class ChildRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IChildRepository{
    override fun getChildren(): {
        return 0
    }

    companion object {
        @Volatile
        private var instance: ChildRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            appExecutors: AppExecutors
        ): ChildRepository =
            instance ?: synchronized(this) {
                instance ?: ChildRepository(remoteData, appExecutors)
            }
    }
}