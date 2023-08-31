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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.ext.isValidPassword
import com.ykis.ykispam.core.ext.passwordMatches
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.SendEmailVerificationResponse
import com.ykis.ykispam.firebase.model.service.repo.SignUpResponse
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUpUiState
import com.ykis.ykispam.navigation.SIGN_IN_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.ykis.ykispam.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val configurationService: ConfigurationService,
    logService: LogService
) : BaseViewModel(logService) {
    var agreementTitle by mutableStateOf(configurationService.agreementTitle)
        private set

    var agreementText by mutableStateOf(configurationService.agreementText)
        private set

    fun init() {
        launchCatching { configurationService.fetchConfiguration() }
    }

    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )
        private set


    var signUpUiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = signUpUiState.value.email
    private val password
        get() = signUpUiState.value.password


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
            signUpResponse = Response.Loading
            signUpResponse = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
        }
    }

    fun sendEmailVerification(openScreen: (String) -> Unit) {
        launchCatching {
            sendEmailVerificationResponse = Response.Loading
            sendEmailVerificationResponse = firebaseService.sendEmailVerification()
            openScreen(SPLASH_SCREEN)
        }

    }

    fun navigateBack(openScreen: (String) -> Unit) {
        openScreen(SIGN_IN_SCREEN)
    }

    fun showVerifyEmailMessage() {
        SnackbarManager.showMessage(AppText.verify_email_message)

    }
}
