package com.ykis.ykispam.data.cache.service

import com.ykis.ykispam.domain.service.ServiceEntity

interface ServiceCache {
    fun addService(service: List<ServiceEntity>)
    fun getServiceDetail(addressId: Int, service: String, year:String): List<ServiceEntity>
    fun deleteAllService()
    fun getTotalDebt(addressId: Int): ServiceEntity?
    fun deleteServiceByApartment(addressIds: List<Int>)


}