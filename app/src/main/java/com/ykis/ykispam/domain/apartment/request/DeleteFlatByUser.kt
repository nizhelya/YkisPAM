package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteApartment @Inject constructor(
    private val repository: ApartmentRepository,
    private val appDatabase: AppDatabase
){
    operator fun invoke (addressId:Int,uid:String) : Flow<Resource<BaseResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.deleteApartment(
                addressId,uid
            )
            if(response.success==1){
                emit(Resource.Success(response))
                appDatabase.apartmentDao().deleteFlat(addressId)
            }
            else throw ExceptionWithResourceMessage(R.string.error_delete_flat)
        } catch (e: ExceptionWithResourceMessage) {
            emit(Resource.Error(resourceMessage = e.resourceMessage,message = null))
        }catch (e:IOException){
            emit(Resource.Error(resourceMessage = R.string.error_network_delete))
        }
        catch (ex:Exception){
            emit(Resource.Error(message = ex.message ))
        }
    }
}