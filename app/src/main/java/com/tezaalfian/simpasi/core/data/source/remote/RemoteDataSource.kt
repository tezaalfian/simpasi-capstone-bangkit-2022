package com.tezaalfian.simpasi.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiResponse
import com.tezaalfian.simpasi.core.data.source.remote.network.ApiService
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getChildren(token: String): LiveData<ApiResponse<List<ChildResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<ChildResponse>>>()

        //get data from remote api
        val client = apiService.getChildren(token)
        client.enqueue(object : Callback<List<ChildResponse>> {
            override fun onResponse(
                call: Call<List<ChildResponse>>,
                response: Response<List<ChildResponse>>
            ) {
                val dataArray = response.body()
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }
            override fun onFailure(call: Call<List<ChildResponse>>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}

