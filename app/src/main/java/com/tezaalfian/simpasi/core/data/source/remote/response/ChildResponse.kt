package com.tezaalfian.simpasi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChildResponse(

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("tglLahir")
	val tglLahir: String? = null,

	@field:SerializedName("umur")
	val umur: Int? = null,

	@field:SerializedName("tb_bayi")
	val tbBayi: Int? = null,

	@field:SerializedName("bb_bayi")
	val bbBayi: Int? = null,

	@field:SerializedName("alergi")
	val alergi: String? = null,

	@field:SerializedName("user")
	val user: String,

	@field:SerializedName("jk_bayi")
	val jkBayi: String? = null,

	@field:SerializedName("tglTerdaftar")
	val tglTerdaftar: String? = null
)
