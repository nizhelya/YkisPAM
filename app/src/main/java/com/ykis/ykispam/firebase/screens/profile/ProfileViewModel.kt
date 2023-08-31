package com.ykis.ykispam.firebase.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.ReloadUserResponse
import com.ykis.ykispam.firebase.model.service.repo.RevokeAccessResponse
import com.ykis.ykispam.firebase.model.service.repo.SendEmailVerificationResponse
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.navigation.EXIT_SCREEN
import com.ykis.ykispam.navigation.PROFILE_SCREEN
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val logService: LogService
) : BaseViewModel(logService) {

    val uid get() = firebaseService.uid
    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email
    val providerId get() = firebaseService.providerId
    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.Success(false))
        private set

    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )
        private set

    val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false

    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Response.Success(false))
        private set

    fun repeatEmailVerified() {
        launchCatching {
            sendEmailVerificationResponse = Response.Loading
            sendEmailVerificationResponse = firebaseService.sendEmailVerification()
            SnackbarManager.showMessage(R.string.verify_email_message)
        }
    }

    fun reloadUser() {
        launchCatching {
            reloadUserResponse = Response.Loading
            reloadUserResponse = firebaseService.reloadFirebaseUser()
        }
    }


    fun signOut(restartApp: (String) -> Unit) {
        launchCatching {
            signOutResponse = Response.Loading
            signOutResponse = firebaseService.signOut()
            restartApp(SPLASH_SCREEN)

        }
    }

    fun revokeAccess() {
        launchCatching {
            revokeAccessResponse = Response.Loading
            revokeAccessResponse = firebaseService.revokeAccess()
            when (revokeAccessResponse) {
                is Response.Success -> Unit
                is Response.Failure -> {
                    launchCatching() {
                        revokeAccessResponse = firebaseService.revokeAccessEmail()
                    }
                }

                else -> Unit
            }
        }
    }


    fun navigateToProfileScreen(restartApp: (String) -> Unit) {
        restartApp(PROFILE_SCREEN)
    }

    fun restartApp(restartApp: (String) -> Unit) {
        launchCatching {
            restartApp(SPLASH_SCREEN)
        }
    }
}