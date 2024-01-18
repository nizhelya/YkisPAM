package com.ykis.ykispam.firebase.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.firestore.FirebaseFirestore
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
import com.ykis.ykispam.navigation.LAUNCH_SCREEN
import com.ykis.ykispam.navigation.YkisRoute
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
    val providerId get() = firebaseService.getProvider(viewModelScope)

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.Success(false))
        private set

    fun signOut() {
        launchCatching {
            signOutResponse = Response.Loading
            signOutResponse = firebaseService.signOut()
//            restartApp(LAUNCH_SCREEN)
        }
    }

    fun revokeAccess() {
        launchCatching {
            revokeAccessResponse = Response.Loading
            if (providerId == "password") {
                revokeAccessResponse = firebaseService.revokeAccessEmail()
            } else if (providerId == "google.com") {
                revokeAccessResponse = firebaseService.revokeAccess()
            }
        }
    }
}