package com.ykis.ykispam.firebase.screens.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.OneTapSignInResponse
import com.ykis.ykispam.firebase.model.service.repo.SignInWithGoogleResponse
import com.ykis.ykispam.firebase.screens.sign_in.components.SingInUiState
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.navigation.PROFILE_SCREEN
import com.ykis.ykispam.navigation.SIGN_UP_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ykis.ykispam.R.string as AppText

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    val oneTapClient: SignInClient,
    logService: LogService
) : BaseViewModel(logService) {

    var singInUiState by mutableStateOf(SingInUiState())
        private set
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(Response.Success(null))
        private set
    private val email
        get() = singInUiState.email
    private val password
        get() = singInUiState.password

    fun onEmailChange(newValue: String) {
        singInUiState = singInUiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        singInUiState = singInUiState.copy(password = newValue)
    }


    fun onSignInClick(openScreen: (String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            firebaseService.firebaseSignInWithEmailAndPassword(email, password)
            openScreen(APARTMENT_SCREEN)
        }

    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            firebaseService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Response.Loading
        oneTapSignInResponse = firebaseService.oneTapSignInWithGoogle()

    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        launchCatching {
            oneTapSignInResponse = Response.Loading
            signInWithGoogleResponse = firebaseService.firebaseSignInWithGoogle(googleCredential)
        }
    }

    fun onSignUpClick(openScreen: (String) -> Unit) {
        openScreen(SIGN_UP_SCREEN)
    }

    fun navigateToApartmentScreen(openScreen: (String) -> Unit) {
        launchCatching {
            openScreen(SPLASH_SCREEN)
        }
    }
}
