package com.ykis.ykispam.pam.data.remote.appartment

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppartmentRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : AppartmentRemote {

    override fun getAppartmentsByUser(uid: String): Either<Failure, List<AppartmentEntity>> {
        return request.make(
            apiService.getAppartmentsByUser(
                createGetAppartmentsByUserMap(uid)
            )
        ) {
            it.appartments
        }
    }

    override fun deleteFlatByUser(
        addressId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteFlatByUser(
                createRequestByAddressId(addressId,uid)
            )
        ) {
            it
        }
    }

    override fun updateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String,
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.updateBti(
                createUpdateBti(
                    addressId,
                    phone,
                    email,
                    uid
                )
            )
        ) {
            it
        }
    }

    override fun getFlatById(
        addressId: Int,
        uid: String
    ): Either<Failure, AppartmentEntity> {
        return request.make(
            apiService.getFlatById(
                createRequestByAddressId(
                    addressId,uid)
            )
        ) {
            it.appartments[0]
        }
    }

    private fun createGetAppartmentsByUserMap(uid: String): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

    private fun createRequestByAddressId(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.PARAM_ADDRESS_ID, addressId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

    private fun createUpdateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.ADDRESS_ID, addressId.toString())
        map.put(ApiService.PHONE, phone)
        map.put(ApiService.EMAIL, email)
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }
}