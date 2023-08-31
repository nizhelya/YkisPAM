package com.ykis.ykispam.firebase.screens.sign_up

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.composable.BasicLinkButton
import com.ykis.ykispam.core.composable.EmailField
import com.ykis.ykispam.core.composable.LogoImage
import com.ykis.ykispam.core.composable.PasswordField
import com.ykis.ykispam.core.composable.RegularCardEditor
import com.ykis.ykispam.core.composable.RepeatPasswordField
import com.ykis.ykispam.core.ext.card
import com.ykis.ykispam.core.ext.spacer
import com.ykis.ykispam.core.ext.textButton
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.screens.sign_up.components.AgreementChekBox
import com.ykis.ykispam.firebase.screens.sign_up.components.SendEmailVerification
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUp
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUpTopBar
import kotlinx.coroutines.delay
import com.ykis.ykispam.R.drawable as AppIcon
import com.ykis.ykispam.R.string as AppText
import com.ykis.ykispam.R.xml as AppConfig

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
//                .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val signUpUiState by viewModel.signUpUiState
        val keyboard = LocalSoftwareKeyboardController.current
        var checkedState by rememberSaveable { mutableStateOf(false) }
        SignUpTopBar(navigateBack = { viewModel.navigateBack(openScreen) })
        Spacer(modifier = Modifier.spacer())
        LogoImage()
        Spacer(modifier = Modifier.spacer())
        if (!checkedState) {
            Text(
                text = viewModel.agreementTitle.ifEmpty {
                    stringResource(id = AppText.agreement_title)
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp, 0.dp),
                style = MaterialTheme.typography.titleSmall
            )
            Card(
                modifier = Modifier.padding(PaddingValues(8.dp)),
                elevation = CardDefaults.cardElevation(20.dp),
                shape = RoundedCornerShape(20.dp),
            )
            {
                Text(
                    text = viewModel.agreementText.ifEmpty {
                        stringResource(id = AppText.agreement_text)
                    },
                    modifier = Modifier.padding(PaddingValues(8.dp)),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium

                )
            }
        }
        AgreementChekBox(
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            modifier = Modifier
        )
        if (checkedState) {
            Column(
                modifier = Modifier.padding(PaddingValues(8.dp))
                    .widthIn(0.dp, 480.dp),
                )
            {
                EmailField(signUpUiState.email, viewModel::onEmailChange, modifier)
                PasswordField(signUpUiState.password, viewModel::onPasswordChange, modifier)
                RepeatPasswordField(
                    signUpUiState.repeatPassword,
                    viewModel::onRepeatPasswordChange,
                    modifier
                )

                RegularCardEditor(
                    AppText.create_account_sign,
                    AppIcon.ic_create_account,
                    "",
                    Modifier.card()
                ) {
                    keyboard?.hide()
                    viewModel.signUpWithEmailAndPassword()
                }
            }


            BasicLinkButton(AppText.alredy_user, Modifier.textButton()) {
                viewModel.navigateBack(openScreen)
            }

        }

    }
    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification(openScreen)
        },
        showVerifyEmailMessage = {
            SnackbarManager.showMessage(AppText.verify_email_message)
        }
    )

    SendEmailVerification()
}
