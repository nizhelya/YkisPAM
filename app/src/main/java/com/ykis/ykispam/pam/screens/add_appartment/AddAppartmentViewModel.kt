/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.pam.screens.add_appartment

import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.SIGN_UP_SCREEN
import com.ykis.ykispam.pam.data.cache.appartment.AppartmentCacheImpl
import com.ykis.ykispam.pam.domain.appartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.appartment.request.GetAppartments
import com.ykis.ykispam.pam.domain.appartment.request.GetFlatById
import com.ykis.ykispam.pam.domain.appartment.request.UpdateBti
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAppartmentViewModel @Inject constructor(
    private val configurationService: ConfigurationService,
    private val getAppartmentsUseCase: GetAppartments,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val updateBtiUseCase: UpdateBti,
    private val getFlatByIdUseCase : GetFlatById,
    private val appartmentCacheImpl: AppartmentCacheImpl,
    logService: LogService
    ) : BaseViewModel(logService) {
//    private val _appartment = MutableLiveData<AppartmentEntity>()
//    val appartment: LiveData<AppartmentEntity> get() = _appartment
//
//    private val _appartments = MutableLiveData<List<AppartmentEntity>>()
//    val appartments: LiveData<List<AppartmentEntity>> get() = _appartments
//
//    private val _address = MutableLiveData<List<AddressEntity>>()
//    val address: LiveData<List<AddressEntity>> get() = _address

//    private val _resultText = MutableLiveData<GetSimpleResponse>()
//    val resultText: LiveData<GetSimpleResponse> = _resultText
//
//    var currentAddress:Int = 0
//    var currentHouse:Int = 0
//    fun deleteFlat(addressId:Int){
//        deleteFlatByUser(addressId){
//                it -> it.either(::handleFailure){
//            handleResultText(
//                it , _resultText
//            )
//        }
//        }
//    }
fun onAddClick(openScreen: (String) -> Unit) {
    openScreen(SIGN_UP_SCREEN)
}
//    fun getAppartmentsByUser(needFetch: Boolean = false) {
//        getAppartmentsUseCase(needFetch) { it ->
//            it.either(::handleFailure) {
//                handleAppartments(
//                    it, !needFetch
//                )
//            }
//        }
//    }
//    fun getFlatFromCache(addressId: Int){
//        viewModelScope.launch {
//            _appartment.value = appartmentCacheImpl.getAppartmentById(addressId)
//        }
//    }
//    fun updateBti(addressId:Int , phone:String , email:String){
//        updateBtiUseCase(AppartmentEntity(addressId = addressId , phone = phone , email = email)){
//                it -> it.either(::handleFailure){
//            handleResultText(
//                it , _resultText
//            )
//        }
//        }
//    }
//    fun getAppartment(appartmentEntity: AppartmentEntity){
//        _appartment.value = appartmentEntity
//    }
//    private fun handleAppartments(appartments: List<AppartmentEntity>, fromCache: Boolean) {
//        _appartments.value = appartments
//        updateProgress(false)
//
//        if (fromCache) {
//            updateProgress(true)
//            getAppartmentsByUser(true)
//        }
//    }
//
//    private fun handleResultText(result: GetSimpleResponse, liveData: MutableLiveData<GetSimpleResponse>){
//        liveData.value = result
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        getAppartmentsUseCase.unsubscribe()
//        deleteFlatByUser.unsubscribe()
//        updateBtiUseCase.unsubscribe()
//    }
}

