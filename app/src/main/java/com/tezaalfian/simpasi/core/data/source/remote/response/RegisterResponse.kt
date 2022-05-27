package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("confirm_password")
    val confirmPassword: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null
)
