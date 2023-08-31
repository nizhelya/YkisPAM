package com.ykis.ykispam.pam.domain.apartment.request

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class DeleteFlatByUser @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int) = apartmentRepository.deleteFlatByUser(params)
}