package com.ykis.ykispam.pam.domain.apartment.request

import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class GetFlatById @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<ApartmentEntity, Int>() {

    override suspend fun run(params: Int) = apartmentRepository.getFlatById(params)
}