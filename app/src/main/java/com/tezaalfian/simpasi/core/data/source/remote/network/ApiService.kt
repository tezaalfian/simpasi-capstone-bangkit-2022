package com.tezaalfian.simpasi.core.data.source.remote.network

import com.tezaalfian.simpasi.core.data.model.Feedback
import com.tezaalfian.simpasi.core.data.source.remote.response.*
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ):RegisterResponse

    @FormUrlEncoded
    @POST("user/login")
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

    @Headers("Content-Type: application/json")
    @PUT("bayi/{id}/bahan")
    suspend fun bahan(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Body alergi: Feedback
    ): UpdateChildResponse

    @DELETE("bayi/{id}")
    suspend fun deleteChild(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): DeleteChildResponse

    @FormUrlEncoded
    @PUT("user/{id}")
    suspend fun editProfile(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("username") username: String
    ): UpdateChildResponse

    @FormUrlEncoded
    @PUT("user/change-pw/{id}")
    suspend fun changePassword(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): ChangePasswordResponse
}