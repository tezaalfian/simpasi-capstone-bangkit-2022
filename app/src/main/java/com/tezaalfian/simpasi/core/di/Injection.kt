package com.tezaalfian.simpasi.core.di

import android.content.Context
import com.tezaalfian.simpasi.core.data.source.local.ChildDataSource
import com.tezaalfian.simpasi.core.data.source.local.room.SimpasiDatabase
import com.tezaalfian.simpasi.core.data.source.remote.RemoteDataSource
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiConfig
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.domain.repository.IChildRepository
import com.tezaalfian.simpasi.core.domain.usecase.ChildInteractor
import com.tezaalfian.simpasi.core.domain.usecase.ChildUseCase
import com.tezaalfian.simpasi.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): IChildRepository {
        val database = SimpasiDatabase.getDatabase(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val localDataSource = ChildDataSource.getInstance(database.childDao())
        val appExecutors = AppExecutors()

        return ChildRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideChildUseCase(context: Context): ChildUseCase {
        val repository = provideRepository(context)
        return ChildInteractor(repository)
    }
}