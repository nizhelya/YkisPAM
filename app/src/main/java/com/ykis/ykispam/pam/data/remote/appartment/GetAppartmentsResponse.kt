package com.ykis.ykispam.pam.data.remote.appartment

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

class GetApartmentsResponse(
    success: Int,
    message: String,
    val apartments: List<ApartmentEntity>
) : BaseResponse(success, message)