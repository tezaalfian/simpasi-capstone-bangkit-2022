package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DeleteChildResponse(

	@field:SerializedName("acknowledged")
	val acknowledged: Boolean? = null,

	@field:SerializedName("deletedCount")
	val deletedCount: Int? = null
)
