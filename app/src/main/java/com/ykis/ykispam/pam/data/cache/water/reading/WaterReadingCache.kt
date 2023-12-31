package com.ykis.ykispam.pam.data.cache.water.reading

import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity

interface WaterReadingCache {
    fun insertWaterReading(waterReading: List<WaterReadingEntity>)
    fun getWaterReading(vodomerId: Int): List<WaterReadingEntity>
    fun deleteAllWaterReading()
    fun deleteWaterReadingFromFlat(addressId:List<Int>)
}