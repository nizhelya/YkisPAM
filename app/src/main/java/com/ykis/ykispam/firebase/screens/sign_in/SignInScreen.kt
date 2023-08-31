package com.ykis.ykispam.firebase.screens.sign_in

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.ykis.ykispam.core.composable.BasicLinkButton
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.composable.EmailField
import com.ykis.ykispam.core.composable.LogoImage
import com.ykis.ykispam.core.composable.PasswordField
import com.ykis.ykispam.core.composable.RegularCardEditor
import com.ykis.ykispam.core.ext.card
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.core.ext.textButton
import com.ykis.ykispam.firebase.screens.sign_in.components.SignInWithGoogle
import com.ykis.ykispam.firebase.screens.sign_in.components.OneTapSignIn
import com.ykis.ykispam.R.drawable as AppIcon
import com.ykis.ykispam.R.string as AppText


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {

    Row( modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
//        .verticalScroll(rememberScrollState())
//        .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = modifier
                .widthIn(0.dp, 460.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
//                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val singInUiState = viewModel.singInUiState
            val keyboard = LocalSoftwareKeyboardController.current
            BasicToolbar(AppText.login_details, isFullScreen = true)
            Spacer(modifier = Modifier.smallSpacer())
            LogoImage()
            Spacer(modifier = Modifier.smallSpacer())
            Column(
                modifier = Modifier.padding(PaddingValues(8.dp))
                    .widthIn(0.dp, 480.dp),
            )
            {
                EmailField(singInUiState.email, viewModel::onEmailChange, modifier)
                PasswordField(singInUiState.password, viewModel::onPasswordChange, modifier)

                RegularCardEditor(
                    AppText.sign_in,
                    AppIcon.ic_action_email,
                    "",
                    Modifier.card()
                ) {
                    keyboard?.hide()
                    viewModel.onSignInClick(openScreen)
                }
                Spacer(modifier = Modifier.smallSpacer())
                BasicLinkButton(AppText.forgot_password, Modifier.textButton()) {
                    viewModel.onForgotPasswordClick()
                }
                Spacer(modifier = Modifier.smallSpacer())
                BasicLinkButton(AppText.no_account, Modifier.textButton()) {
                    viewModel.onSignUpClick(openScreen)
                }
                Spacer(modifier = Modifier.smallSpacer())
                RegularCardEditor(
                    AppText.sign_in_with_google,
                    AppIcon.ic_google_logo,
                    "",
                    Modifier.card()
                ) {
                    viewModel.oneTapSignIn()
                }
            }
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    SignInWithGoogle(
        navigateToHomeScreen = { signedIn ->
            if (!signedIn) {
                viewModel.navigateToApartmentScreen(openScreen)
            }
        }
    )

}

