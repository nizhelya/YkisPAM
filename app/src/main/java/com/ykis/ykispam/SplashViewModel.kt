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
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.SIGN_IN_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.navigation.VERIFY_EMAIL_SCREEN
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.navigation.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    logService: LogService
) : BaseViewModel(logService) {
    val showError = mutableStateOf(false)
    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false
    init {
        getAuthState()
    }
    fun getAuthState() = firebaseService.getAuthState(viewModelScope)
    fun onAppStart(isUserSignedOut: Boolean, restartApp: (String) -> Unit) {
        showError.value = false
        if (isUserSignedOut) {
            restartApp(SIGN_IN_SCREEN)
        } else {
            if (isEmailVerified) {
                restartApp(APARTMENT_SCREEN)
            } else {
                restartApp(VERIFY_EMAIL_SCREEN)
            }
        }
    }

}
