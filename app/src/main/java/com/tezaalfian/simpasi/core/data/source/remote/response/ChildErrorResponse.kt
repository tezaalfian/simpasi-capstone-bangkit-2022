package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChildErrorResponse(

	@field:SerializedName("message")
	val message: Message? = null
)

data class Properties(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Message(

	@field:SerializedName("_message")
	val _message: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("message")
	val message: String? = null,
)
