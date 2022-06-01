package com.tezaalfian.simpasi.core.data.source.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.User
import com.tezaalfian.simpasi.core.data.source.remote.response.LoginResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.RegisterResponse
import retrofit2.HttpException
import java.lang.Exception

class AuthRepository private constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiService
){

    fun login(usernameEmail: String, password: String) : LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val result = apiService.login(usernameEmail, password)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Resource.Error(it.toString()))
                }
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun register(nama: String, email: String, username: String, password: String, confirm_password: String) : LiveData<Resource<RegisterResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val result = apiService.register(nama, email, username, password, confirm_password)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Resource.Error(it.toString()))
                }
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun getToken() : Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    fun getUser() : Flow<User> {
        return dataStore.data.map { pref ->
            User(
                pref[NAME] ?: "",
                pref[USERNAME] ?: "",
                pref[EMAIL] ?: ""
            )
        }
    }

    suspend fun setUserData(token: String, name: String, email: String, username: String){
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[NAME] = name
            preferences[USERNAME] = username
            preferences[EMAIL] = email
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        private val TOKEN = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")
        private val USERNAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")

        fun getInstance(
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthRepository(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}