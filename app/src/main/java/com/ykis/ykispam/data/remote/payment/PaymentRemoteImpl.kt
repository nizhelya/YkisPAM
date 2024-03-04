package com.ykis.ykispam.data.remote.payment

import com.ykis.ykispam.data.remote.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : PaymentRemote {
//
//    override fun getFlatPayments(
//        addressId: Int,
//        uid: String
//    ): Either<Failure, List<PaymentEntity>> {
//        return request.make(
//            apiService.getFlatPayment(
//                createGetFlatPaymentMap(
//                    addressId,
//                    uid
//                )
//            )
//        ) {
//            it.payments
//        }
//    }

    private fun createGetFlatPaymentMap(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.UID] = uid
        return map
    }
}