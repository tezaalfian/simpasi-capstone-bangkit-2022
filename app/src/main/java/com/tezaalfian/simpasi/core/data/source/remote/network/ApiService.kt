package com.tezaalfian.simpasi.core.data.source.remote.network

import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.LoginResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.RegisterResponse
import com.tezaalfian.simpasi.core.data.source.remote.response.UpdateChildResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ):RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("usernameEmail") usernameEmail: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("bayi")
    fun getChildren(
        @Header("auth-token") token: String,
    ): Call<List<ChildResponse>>

    @GET("bayi/{id}")
    suspend fun getChild(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ChildResponse

    @POST("bayi")
    suspend fun addChild(
        @Header("Authorization") token: String,
        @Field("nama") nama: String,
        @Field("tglLahir") tglLahir: String,
        @Field("umur") umur: Int,
        @Field("jk_bayi") jk_bayi: Gender,
        @Field("tb_bayi") tb_bayi: Int,
        @Field("bb_bayi") bb_bayi: Int,
        @Field("alergi") alergi: String,
    ): ChildResponse

    @PUT("bayi/{id}")
    suspend fun editChild(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("nama") nama: String,
        @Field("tglLahir") tglLahir: String,
        @Field("umur") umur: Int,
        @Field("jk_bayi") jk_bayi: Gender,
        @Field("tb_bayi") tb_bayi: Int,
        @Field("bb_bayi") bb_bayi: Int,
        @Field("alergi") alergi: String,
    ): UpdateChildResponse

    @DELETE("bayi/{id}")
    suspend fun deleteChild(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ChildResponse
}

enum class Gender(val s: String) {
    L("Laki-laki"),
    P("Perempuan")
}