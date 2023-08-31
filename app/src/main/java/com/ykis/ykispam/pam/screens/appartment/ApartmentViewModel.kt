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
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.PROFILE_SCREEN
import com.ykis.ykispam.navigation.SETTINGS_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.pam.screens.add_apartment.SecretKeyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    private val logService: LogService
) : BaseViewModel(logService) {

    var secretKeyUiState by mutableStateOf(SecretKeyUiState())
        private set
    private val secretCode
        get() = secretKeyUiState.secretCode


    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    var uiState: StateFlow<HomeUIState> = _uiState
    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email


    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _apartments = MutableLiveData<List<ApartmentEntity>>()
    val apartments: LiveData<List<ApartmentEntity>> get() = _apartments

    private val _address = MutableLiveData<List<AddressEntity>>()
    val address: LiveData<List<AddressEntity>> get() = _address

    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType

    var networkInfo = mutableStateOf(true)



   fun initialize() {
        launchCatching {
            updateProgress(true)
            _uiState.value = _uiState.value.copy(
                uid = uid,
                displayName = displayName,
                email = email,
                photoUrl = photoUrl
            )
            if (isConnected && networkType == 2) {
                networkInfo.value = true
                getApartmentsByUserfromServer(true)
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                networkInfo.value = false
                getApartmentsByUserfromCache(false)
            }
        }
    }
    fun getApartmentsByUserfromServer(needFetch: Boolean = true) {
        updateProgress(true)
        getApartmentsUseCase(needFetch) { it ->

            if (it.isRight) {
                it.either(::handleFailure) {
                    handleApartments(it)
                }
            } else {
                getApartmentsByUserfromCache(false)
                SnackbarManager.showMessage(R.string.error_server_appartment)
            }
        }
    }

    fun getApartmentsByUserfromCache(needFetch: Boolean = false) {
        getApartmentsUseCase(needFetch) { it ->
            it.either(::handleFailure) {
                handleApartments(it)
            }

        }
    }

    fun getFlatFromCache(addressId: Int, openScreen: (String) -> Unit) {
        viewModelScope.launch {
            _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
            openScreen(PROFILE_SCREEN)

        }
    }

    fun deleteFlat(addressId: Int, popUpScreen: (String) -> Unit) {

        deleteFlatByUser(addressId) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
//        initialize()
        popUpScreen(SPLASH_SCREEN)

    }

    fun updateBti(addressId: Int, phone: String, email: String) {
        updateBtiUseCase(
            ApartmentEntity(
                addressId = addressId,
                phone = phone,
                email = email
            )
        ) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
    }

    private fun handleApartments(appartments: List<ApartmentEntity>) {
        _apartments.value = appartments
        _uiState.value = _uiState.value.copy(apartments = appartments)
        updateProgress(false)
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(ADD_APARTMENT_SCREEN)
        }
    }

    fun onSettingsClick(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(SETTINGS_SCREEN)
        }
    }

    fun onExitAppClick(restartApp: (String) -> Unit) {
        launchCatching {
            signOutResponse = Response.Loading
            signOutResponse = firebaseService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onSecretCodeChange(newValue: String) {
        secretKeyUiState = secretKeyUiState.copy(secretCode = newValue)
    }

    fun onAddAppartmentClick(popUpScreean: () -> Unit) {
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }

        addFlatByUser(secretCode) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it, _resultText
                )
            }
        }
        popUpScreean()
//        if (resultText.value?.success == 1) {
//            restartApp(APPARTMENT_SCREEN)
//        }

    }


    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
    }

    override fun onCleared() {
        super.onCleared()
        getApartmentsUseCase.unsubscribe()
        deleteFlatByUser.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }

}

data class HomeUIState(
    val uid: String? = null,
    val displayName: String? = null,
    val email:String? = null,
    val photoUrl: String? = null,
    var apartments: List<ApartmentEntity> = emptyList(),
    val selectedApartment: ApartmentEntity? = null,
    val isEmailVerified: Boolean = false,
    val isUserSignedOut: Boolean = false,
    val isDetailOnlyOpen: Boolean = false,
    val selectedDestination: String? = null,
    val secretCode: String = "",
    val loading: Boolean = false,
    val error: String? = null
)