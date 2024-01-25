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

package com.ykis.ykispam.firebase.screens.sign_up

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.ext.isValidPassword
import com.ykis.ykispam.core.ext.passwordMatches
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.ReloadUserResponse
import com.ykis.ykispam.firebase.model.service.repo.SendEmailVerificationResponse
import com.ykis.ykispam.firebase.model.service.repo.SignUpResponse
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUpUiState
import com.ykis.ykispam.navigation.LAUNCH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.ykis.ykispam.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val configurationService: ConfigurationService,
    logService: LogService
) : BaseViewModel(logService) {

    private val  _agreementTitle = MutableStateFlow(configurationService.agreementTitle)
    val agreementTitle = _agreementTitle.asStateFlow()

    private val  _agreementText = MutableStateFlow(configurationService.agreementText)
    val agreementText = _agreementText.asStateFlow()

    private val _reloadUserResponse = MutableStateFlow<ReloadUserResponse>(Response.Success(false))
    val reloadUserResponse = _reloadUserResponse.asStateFlow()

    private val _signUpResponse = MutableStateFlow<SignUpResponse>(Response.Success(false))
    val signUpResponse = _signUpResponse.asStateFlow()

    private val _sendEmailVerificationResponse = MutableStateFlow<SendEmailVerificationResponse>(Response.Success(false))

    var signUpUiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = signUpUiState.value.email
    private val password
        get() = signUpUiState.value.password



    val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false

    init{
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun repeatEmailVerified() {
        launchCatching {
            _sendEmailVerificationResponse.value = Response.Loading
            _sendEmailVerificationResponse.value = firebaseService.sendEmailVerification()
            SnackbarManager.showMessage(R.string.verify_email_message)
        }
    }
    fun onEmailChange(newValue: String) {
        signUpUiState.value = signUpUiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        signUpUiState.value = signUpUiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        signUpUiState.value = signUpUiState.value.copy(repeatPassword = newValue)
    }

    fun signUpWithEmailAndPassword() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(signUpUiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            _signUpResponse.value = Response.Loading
            _signUpResponse.value = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
        }
    }

    fun sendEmailVerification(openScreen: (String) -> Unit) {
        launchCatching {
            _sendEmailVerificationResponse.value = Response.Loading
            _sendEmailVerificationResponse.value = firebaseService.sendEmailVerification()
            openScreen(LAUNCH_SCREEN)
        }

    }
    fun navigateToLaunchScreen(restartApp: (String) -> Unit) {
        restartApp(LAUNCH_SCREEN)
    }
    fun reloadUser() {
        launchCatching {
            _reloadUserResponse.value = Response.Loading
            _reloadUserResponse.value = firebaseService.reloadFirebaseUser()
        }
    }
    fun navigateBack(openScreen: (String) -> Unit) {
        openScreen(LAUNCH_SCREEN)
    }
    override fun onCleared() {
        Log.d("sharedViewModel", "vm is cleared!")
        super.onCleared()
    }
}
