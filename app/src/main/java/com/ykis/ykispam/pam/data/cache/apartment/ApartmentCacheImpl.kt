package com.ykis.ykispam.pam.data.cache.apartment

import com.ykis.ykispam.pam.data.cache.dao.ApartmentDao
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApartmentCacheImpl @Inject constructor(
    private val apartmentDao: ApartmentDao
): ApartmentCache {


    override fun addApartmentByUser(apartment: List<ApartmentEntity>) {
        apartmentDao.addApartmentByUser(apartment)
    }

    override fun getApartmentsByUser(): List<ApartmentEntity> {
        return apartmentDao.getApartmentsByUser()
    }


    override fun deleteAllApartments() {
        apartmentDao.deleteAllApartments()
    }

    override fun deleteFlat(addressId: Int) {
        apartmentDao.deleteFlat(addressId)
    }

    override fun getApartmentById(addressId: Int): ApartmentEntity {
        return apartmentDao.getFlatById(addressId)
    }


}