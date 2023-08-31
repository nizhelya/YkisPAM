package com.ykis.ykispam.pam.domain.apartment.request

import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class GetApartments @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<List<ApartmentEntity>, Boolean>() {

    override suspend fun run(needFetch: Boolean) =
        apartmentRepository.getApartmentsByUser(needFetch)
}