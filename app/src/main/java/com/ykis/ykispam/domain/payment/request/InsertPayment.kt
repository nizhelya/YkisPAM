package com.ykis.ykispam.domain.payment.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.payment.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class InsertPayment @Inject constructor(
    private val repository: PaymentRepository,
) {
    operator fun invoke(params:InsertPaymentParams): Flow<Resource<Int?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.insertPayment(
                params
            )
            if (response.success == 1) {
                emit(Resource.Success(response.paymentId))
            }
        }catch (e: HttpException) {
//            SnackbarManager.showMessage(e.message())
//            emit(Resource.Error())
//        } catch (e: IOException) {
//            val paymentList = database.paymentDao().getPaymentFromFlat(addressId)
//            if(paymentList.isNotEmpty()){
//                emit(Resource.Success(paymentList))
//            }
//            SnackbarManager.showMessage(R.string.error_network)
//            emit(Resource.Error())
//        }
//    }
        }
    }
}