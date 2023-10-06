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

package com.ykis.ykispam

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.ADDRESS_DEFAULT_ID
import com.ykis.ykispam.navigation.ADDRESS_ID
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.EXIT_SCREEN
import com.ykis.ykispam.navigation.SIGN_IN_SCREEN
import com.ykis.ykispam.navigation.VERIFY_EMAIL_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.data.remote.core.NetworkHandler
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.request.GetApartments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val getApartmentsUseCase: GetApartments,
    private val apartmentCacheImpl: ApartmentCacheImpl,
    private val networkHandler: NetworkHandler,
    logService: LogService,
) : BaseViewModel(logService) {

    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email

    val showError = mutableStateOf(false)
    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false

        fun getAuthState() = firebaseService.getAuthState(viewModelScope)

        fun onAppStart(
            isUserSignedOut: Boolean,
            openScreen: (String) -> Unit,
            openAndPopUp: (String, String) -> Unit
        ) {
            showError.value = false
            if (isUserSignedOut) {
                openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
            } else {
                if (isEmailVerified ) {
                    _uiState.value = _uiState.value.copy(
                        uid = uid,
                        displayName = displayName,
                        email = email,
                        photoUrl = photoUrl,
                        apartments = listOf(),
                        selectedDestination = "$EXIT_SCREEN?$ADDRESS_ID={${ADDRESS_DEFAULT_ID}}")
                    openAndPopUp(APARTMENT_SCREEN, SPLASH_SCREEN)
                } else {
                    openScreen(VERIFY_EMAIL_SCREEN)
                }
            }
        }

    }