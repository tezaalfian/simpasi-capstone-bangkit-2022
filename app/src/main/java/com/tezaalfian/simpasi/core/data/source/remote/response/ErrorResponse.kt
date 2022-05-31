package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
