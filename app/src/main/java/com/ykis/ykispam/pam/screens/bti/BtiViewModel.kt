package com.ykis.ykispam.pam.screens.bti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUpUiState
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.BTI_SCREEN
import com.ykis.ykispam.navigation.SETTINGS_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.cache.payment.PaymentCacheImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.DeleteFlatByUser
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import com.ykis.ykispam.pam.domain.apartment.request.UpdateBti
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import com.ykis.ykispam.pam.domain.payment.PaymentItemEntity
import com.ykis.ykispam.pam.domain.payment.request.GetFlatPayment
import com.ykis.ykispam.pam.screens.appartment.SecretKeyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BtiViewModel @Inject constructor(
    private val getApartmentsUseCase: GetApartments,
    private val updateBtiUseCase: UpdateBti,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    private val logService: LogService,
) : BaseViewModel(logService) {


    private val isConnected: Boolean get() = networkHandler.isConnected
    private val networkType: Int get() = networkHandler.networkType
    var contactUiState = mutableStateOf(ApartmentEntity())
        private set

    fun onEmailChange(newValue: String) {
        contactUiState.value = contactUiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        contactUiState.value = contactUiState.value.copy(phone = newValue)
    }

    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _apartments = MutableLiveData<List<ApartmentEntity>>()
    val apartments: LiveData<List<ApartmentEntity>> get() = _apartments


    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText


    fun getBtiFromCache(addressId: Int) {
        viewModelScope.launch {
            _apartment.value = apartmentCacheImpl.getApartmentById(addressId)
            contactUiState.value = contactUiState
                .value.copy(
                    addressId = _apartment.value!!.addressId,
                    address = _apartment.value!!.address,
                    email = _apartment.value!!.email,
                    phone = _apartment.value!!.phone,
                )

        }
    }

    fun getApartmentsByUser(needFetch: Boolean = true) {
        getApartmentsUseCase(needFetch) { it ->

            if (it.isRight) {
                it.either(::handleFailure) {
                    handleApartments(it)
                }

            }
        }
    }

    private fun handleApartments(apartments: List<ApartmentEntity>) {
        _apartments.value = apartments


    }

    fun onUpdateBti(openScreen: (String) -> Unit) {


        launchCatching {
            if (!apartment.value?.email?.isValidEmail()!!) {
                SnackbarManager.showMessage(R.string.email_error)
                return@launchCatching
            }
            updateBtiUseCase(
                ApartmentEntity(
                    addressId = contactUiState.value.addressId,
                    phone = contactUiState.value.phone,
                    email = contactUiState.value.email
                )
            ) { it ->
                it.either(::handleFailure) {
                    handleResultText(
                        it, _resultText
                    )
                }

            }
            openScreen(BTI_SCREEN)
        }
    }

    private fun handleResultText(
        response: GetSimpleResponse,
        result: MutableLiveData<GetSimpleResponse>
    ) {
        result.value = response
        if (result.value!!.success == 1) {
            SnackbarManager.showMessage(R.string.updated)
            if (isConnected && networkType == 2) {
                getApartmentsByUser(true)
            } else {
                SnackbarManager.showMessage(R.string.error_server_appartment)
                getApartmentsByUser(false)
            }
        }

    }

    fun navigateBack(popUpScreen: () -> Unit) {
        launchCatching {
            popUpScreen()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getApartmentsUseCase.unsubscribe()
        updateBtiUseCase.unsubscribe()
    }


}
