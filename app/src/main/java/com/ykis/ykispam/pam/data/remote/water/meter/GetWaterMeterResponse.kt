package com.ykis.ykispam.pam.data.remote.water.meter

import com.squareup.moshi.Json
import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity

class GetWaterMeterResponse(
    success: Int,
    message: String,
    @Json(name = "water_meters")
    val waterMeters: List<WaterMeterEntity>
) : BaseResponse(success, message)