package com.ykis.ykispam.pam.domain.apartment

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface ApartmentRepository {
    suspend  fun getApartmentsByUser(needFetch: Boolean): Either<Failure, List<ApartmentEntity>>

    fun deleteFlatByUser(addressId: Int): Either<Failure, GetSimpleResponse>
    fun updateBti(addressId: Int, phone: String, email: String): Either<Failure, GetSimpleResponse>
    fun getFlatById(addressId: Int): Either<Failure, ApartmentEntity>
}