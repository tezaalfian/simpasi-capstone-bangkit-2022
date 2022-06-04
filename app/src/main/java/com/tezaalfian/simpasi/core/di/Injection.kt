package com.tezaalfian.simpasi.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tezaalfian.simpasi.core.data.source.local.room.SimpasiDatabase
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiConfig
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideChildRepository(context: Context) : ChildRepository {
        val database = SimpasiDatabase.getDatabase(context)
        return ChildRepository.getInstance(ApiConfig.getApiService(), database.childDao())
    }

    fun provideUserRepository(context: Context) : UserRepository {
        val database = SimpasiDatabase.getDatabase(context)
        return UserRepository.getInstance(context.dataStore, ApiConfig.getApiService())
    }
}