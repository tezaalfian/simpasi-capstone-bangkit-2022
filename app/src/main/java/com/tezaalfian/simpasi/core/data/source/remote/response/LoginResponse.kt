package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("token")
	val token: String? = null
)
