package com.ykis.ykispam.pam.data.cache.heat.meter

import com.ykis.ykispam.pam.data.cache.dao.HeatMeterDao
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeatMeterCacheImpl @Inject constructor(
    private val heatMeterDao: HeatMeterDao
) : HeatMeterCache {
    override fun insertHeatMeter(waterMeters: List<HeatMeterEntity>) {
        heatMeterDao.insertHeatMeter(waterMeters)
    }

    override fun getHeatMeter(addressId: Int): List<HeatMeterEntity> {
        return heatMeterDao.getHeatMeter(addressId)
    }

    override fun deleteAllHeatMeter() {
        heatMeterDao.deleteAllHeatMeter()
    }
}