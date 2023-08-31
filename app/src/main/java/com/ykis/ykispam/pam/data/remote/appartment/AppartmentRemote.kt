package com.ykis.ykispam.pam.data.remote.appartment

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface ApartmentRemote {
    fun getApartmentsByUser(uid: String): Either<Failure, List<ApartmentEntity>>

    fun deleteFlatByUser(
        addressId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun updateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun getFlatById(addressId: Int,uid: String): Either<Failure, ApartmentEntity>
}