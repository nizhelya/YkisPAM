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

package com.ykis.ykispam.pam.screens.appartment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.RevokeAccessResponse
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.Graph
import com.ykis.ykispam.navigation.LAUNCH_SCREEN
import com.ykis.ykispam.navigation.VERIFY_EMAIL_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentsUseCase: GetApartments,
    private val deleteFlatByUser: DeleteFlatByUser,
    private val addFlatByUser: AddFlatByUser,
    private val updateBtiUseCase: UpdateBti,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {

    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    val uid get() = firebaseService.uid
    private val displayName get() = firebaseService.displayName
    val email get() = firebaseService.email
    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.Success(false))
        private set
    private val secretCode
        get() = secretKeyUiState.secretCode

    private val addressId
        get() = secretKeyUiState.addressId


    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    var secretKeyUiState by mutableStateOf(SecretKeyUiState())
        private set

    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _address = MutableLiveData<List<AddressEntity>>()
    val address: LiveData<List<AddressEntity>> get() = _address

    private val _totalDebt = MutableLiveData<ServiceEntity?>()
    val totalDebt: LiveData<ServiceEntity?> get() = _totalDebt

    private val _totalPay = MutableLiveData<Double>(0.0)
    val totalPay: LiveData<Double> get() = _totalPay


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    // LaunchScreen
    val showError = mutableStateOf(false)
    fun getAuthState() = firebaseService.getAuthState(viewModelScope)

    fun onAppStart(
        isUserSignedOut: Boolean,
        openAndPopUp: (String, String) -> Unit,
        restartApp: (String) -> Unit,

    ) {
        showError.value = false
        if (isUserSignedOut) {
            restartApp(Graph.AUTHENTICATION)
        } else {
            if (isEmailVerified) {
                restartApp(Graph.APARTMENT)
            } else {
                // TODO: make this with authNavGraph
                openAndPopUp(VERIFY_EMAIL_SCREEN, LAUNCH_SCREEN)

            }
        }
    }



    fun initialize() {
        if (uid.isNotEmpty()) {
            observeApartments()
        }
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedContentDetail = ContentDetail.BTI
            )
    }

    private fun observeApartments() {
        launchCatching {
            _uiState.value = _uiState.value.copy(
                uid = uid,
                displayName = displayName,
                email = email,
            )
            if (isConnected && networkType != 0) {
                getApartmentsByUser( true)
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                getApartmentsByUser(false)
            }

        }
    }
//визивається дваждьІ
    fun setApartment(addressId: Int) {
        if (addressId != 0) {
            launchCatching {
                _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
                _uiState.value = _apartment.value?.let {
                    _uiState.value.copy(
                        apartment = _apartment.value!!,
                        addressId = _apartment.value!!.addressId,
                        address = _apartment.value!!.address,
                        houseId = _apartment.value!!.houseId,
                        osmdId = _apartment.value!!.osmdId,
                        osbb = _apartment.value!!.osbb,
                        selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value!!.addressId}}"
                    )
                }!!

            }
        }
    }

    private fun getFlatFromCache(addressId: Int) {
        _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
        _uiState.value = _uiState.value.copy(
            apartment = _apartment.value!!,
            addressId = _apartment.value!!.addressId,
            address = _apartment.value!!.address,
            houseId = _apartment.value!!.houseId,
            osmdId = _apartment.value!!.osmdId,
            osbb = _apartment.value!!.osbb,
            selectedDestination = "$APARTMENT_SCREEN?$ADDRESS_ID={${_apartment.value!!.addressId}}"
        )
    }

    fun setSelectedDetail(contentDetail: ContentDetail, contentType: ContentType) {

        _uiState.value = _uiState.value.copy(
            selectedContentDetail = contentDetail,
            isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
        )
    }

    fun onSecretCodeChange(newValue: String) {
        secretKeyUiState = secretKeyUiState.copy(secretCode = newValue)
    }

    fun addApartment(restartApp: (String) -> Unit) {
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }
        launchCatching {
                addFlatByUser(secretCode) { it ->
//                Log.d("response_test",it.toString())
                it.either(::handleFailure) {
                    handleResultText(
                        it, _resultText
                    )
                }
                if (resultText.value?.success == 1) {
                    _uiState.value = _uiState.value.copy(
                        secretCode = secretCode,
                        addressId = resultText.value!!.addressId
                    )
                    Log.d("response_test",resultText.value?.addressId.toString())
//                    getApartmentsByUser(true)
                    SnackbarManager.showMessage(R.string.success_add_flat)
                    // TODO: rename fun restartApp
                    Log.d("state_test","addApartment:${_uiState.value.addressId}")
                    restartApp(APARTMENT_SCREEN)
                }

            }
        }

    }

    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>,
    ) {
        result.value = response
    }

    fun getApartmentsByUser( needFetch: Boolean = true) {
        getApartmentsUseCase(needFetch) { it ->
            if (it.isRight) {
                it.either(::handleFailure) {
                    handleApartments(it, !needFetch)
                }

            }
        }
    }

    private fun handleApartments(apartments: List<ApartmentEntity>, fromCache: Boolean) {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            apartments = apartments
        )
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getApartmentsByUser(true)
        }
        updateProgress(true)
    }

    fun deleteApartment(addressId: Int, restartApp: (String) -> Unit) {
        launchCatching {

            if (addressId != 0) {
                deleteFlatByUser(addressId) { it ->
                    it.either(::handleFailure) {
                        handleResultTextDelete(
                            it, _resultText
                        )
                    }
                }

            }
        }
        restartApp(APARTMENT_SCREEN)

    }


    private fun handleResultTextDelete(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.success_delete_flat)
            observeApartments()
        }
    }
    fun signOut() {
        launchCatching {
            signOutResponse = Response.Loading
            signOutResponse = firebaseService.signOut()
        }
    }
}




