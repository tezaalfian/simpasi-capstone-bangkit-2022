package com.tezaalfian.simpasi.core.data.source.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.User
import com.tezaalfian.simpasi.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception

class UserRepository private constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiService
){

    fun login(usernameEmail: String, password: String) : Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.login(usernameEmail, password)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun register(nama: String, email: String, username: String, password: String, confirm_password: String) : Flow<Resource<RegisterResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.register(nama, email, username, password, confirm_password)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun editProfile(token: String, id: String, nama: String, email: String, username: String) : Flow<Resource<UpdateChildResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.editProfile(token, id, nama, email, username)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun changePassword(token: String, id: String, password: String, confirm_password: String) : Flow<Resource<ChangePasswordResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.changePassword(token, id, password, confirm_password)
            emit(Resource.Success(result))
        }catch (throwable: HttpException){
            try {
                val errorResponse = Gson().fromJson(throwable.response()?.errorBody()?.source()?.readUtf8().toString(), ErrorResponse::class.java)
                emit(Resource.Error(errorResponse.message.toString()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getToken() : Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    fun getUser() : Flow<User> {
        return dataStore.data.map { pref ->
            User(
                pref[TOKEN] ?: "",
                pref[ID] ?: "",
                pref[NAME] ?: "",
                pref[USERNAME] ?: "",
                pref[EMAIL] ?: ""
            )
        }
    }

    suspend fun setUserData(token: String, id: String, name: String, email: String, username: String){
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[ID] = id
            preferences[NAME] = name
            preferences[USERNAME] = username
            preferences[EMAIL] = email
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
            preferences[ID] = ""
            preferences[NAME] = ""
            preferences[USERNAME] = ""
            preferences[EMAIL] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        private val ID = stringPreferencesKey("id")
        private val TOKEN = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")
        private val USERNAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")

        fun getInstance(
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}