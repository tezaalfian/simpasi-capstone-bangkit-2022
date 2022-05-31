package com.tezaalfian.simpasi.core.di

import android.content.Context
import com.tezaalfian.simpasi.core.data.source.local.room.SimpasiDatabase
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiConfig
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository

object Injection {
    fun provideChildRepository(context: Context) : ChildRepository {
        val database = SimpasiDatabase.getDatabase(context)
        return ChildRepository.getInstance(ApiConfig.getApiService(), database.childDao())
    }
}