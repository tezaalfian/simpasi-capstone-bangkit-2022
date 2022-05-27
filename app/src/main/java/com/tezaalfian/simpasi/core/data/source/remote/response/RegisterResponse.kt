package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("confirm_password")
    val confirmPassword: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null
)
