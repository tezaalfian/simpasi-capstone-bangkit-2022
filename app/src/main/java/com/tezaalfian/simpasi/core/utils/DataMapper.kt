package com.tezaalfian.simpasi.core.utils

import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.domain.model.Child

object DataMapper {
    fun mapEntitiesToDomain(input: List<ChildEntity>): List<Child> =
        input.map { el ->
            Child(
                id = el.id,
                nama = el.nama,
                tglLahir = el.tglLahir,
                umur = el.umur,
                tbBayi = el.tbBayi,
                bbBayi = el.bbBayi,
                alergi = el.alergi,
                user = el.user,
                jkBayi = el.jkBayi,
                tglTerdaftar = el.tglTerdaftar
            )
        }
    fun mapDomainToEntity(el: Child) = ChildEntity(
        id = el.id,
        nama = el.nama,
        tglLahir = el.tglLahir,
        umur = el.umur,
        tbBayi = el.tbBayi,
        bbBayi = el.bbBayi,
        alergi = el.alergi,
        user = el.user,
        jkBayi = el.jkBayi,
        tglTerdaftar = el.tglTerdaftar
    )

    fun mapResponsesToEntities(input: List<ChildResponse>): List<ChildEntity> {
        val tourismList = ArrayList<ChildEntity>()
        input.map { el ->
            val tourism = ChildEntity(
                id = el.id,
                nama = el.nama,
                tglLahir = el.tglLahir,
                umur = el.umur,
                tbBayi = el.tbBayi,
                bbBayi = el.bbBayi,
                alergi = el.alergi,
                user = el.user,
                jkBayi = el.jkBayi,
                tglTerdaftar = el.tglTerdaftar
            )
            tourismList.add(tourism)
        }
        return tourismList
    }
}