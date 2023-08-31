package com.ykis.ykispam.pam.data.cache.apartment

import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity


interface ApartmentCache {
    fun addApartmentByUser(apartment:List<ApartmentEntity>)
    fun getApartmentsByUser():List<ApartmentEntity>

    fun deleteAllApartments()
    fun deleteFlat(addressId: Int)
    fun getApartmentById(addressId: Int): ApartmentEntity
}