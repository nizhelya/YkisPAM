package com.ykis.ykispam.pam.data.cache.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import androidx.room.Query

@Dao
interface HeatMeterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeatMeter(waterMeter:List<HeatMeterEntity>)
    @Query("select *   from heat_meter where address_id = :addressId order by work")
    fun getHeatMeter(addressId:Int): List<HeatMeterEntity>
    @Query("delete from heat_meter")
    fun deleteAllHeatMeter()
}