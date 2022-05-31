package com.tezaalfian.simpasi.core.data.source.remote.network

import com.tezaalfian.simpasi.core.data.source.remote.response.*
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
    suspend fun getChildren(
        @Header("auth-token") token: String,
    ): List<ChildResponse>

    @GET("bayi/{id}")
    suspend fun getChild(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): ChildResponse

    @FormUrlEncoded
    @POST("bayi")
    suspend fun addChild(
        @Header("auth-token") token: String,
        @Field("nama") nama: String,
        @Field("tglLahir") tglLahir: String,
        @Field("jk_bayi") jk_bayi: String? = null,
        @Field("bb_bayi") bb_bayi: Int,
        @Field("alergi") alergi: String? = null
    ): ChildResponse

    @FormUrlEncoded
    @PUT("bayi/{id}")
    suspend fun editChild(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Field("nama") nama: String,
        @Field("tglLahir") tglLahir: String,
        @Field("jk_bayi") jk_bayi: String? = null,
        @Field("bb_bayi") bb_bayi: Int,
        @Field("alergi") alergi: String? = null
    ): UpdateChildResponse

    @DELETE("bayi/{id}")
    suspend fun deleteChild(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): DeleteChildResponse
}