package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.tezaalfian.simpasi.core.data.model.Feedback

data class ChildResponse(

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("tglLahir")
	val tglLahir: String,

	@field:SerializedName("bb_bayi")
	val bbBayi: Int,

	@field:SerializedName("user")
	val user: String,

	@field:SerializedName("jk_bayi")
	val jkBayi: String,

	@field:SerializedName("tglTerdaftar")
	val tglTerdaftar: String? = null,

	@field:SerializedName("alergi")
	val alergi: Feedback? = null,
)
