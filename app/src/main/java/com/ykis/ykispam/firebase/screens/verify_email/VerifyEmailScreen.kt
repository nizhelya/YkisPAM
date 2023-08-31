package com.ykis.ykispam.firebase.screens.verify_email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.composable.LogoImage
import com.ykis.ykispam.core.ext.spacer
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.screens.profile.ProfileViewModel
import com.ykis.ykispam.firebase.screens.verify_email.components.ReloadUser
import com.ykis.ykispam.R.string as AppText

@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    restartApp: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(PaddingValues(4.dp, 4.dp, 4.dp, 4.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        BasicToolbar(AppText.verify_email, isFullScreen = true)

        Spacer(modifier = Modifier.spacer())
        LogoImage()
        Spacer(modifier = Modifier.spacer())
        Text(
            modifier = Modifier.clickable {
                viewModel.reloadUser()
            },
            text = stringResource(AppText.alredy_user),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.spacer())
        Text(
            modifier = Modifier.padding(48.dp),
            text = stringResource(AppText.span_email),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.spacer())

        Text(
            modifier = Modifier.clickable {
                viewModel.repeatEmailVerified()
            },
            text = stringResource(AppText.repeat_email_not_verified_message),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
    }
    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                viewModel.navigateToProfileScreen(restartApp)
            } else {
                SnackbarManager.showMessage(AppText.email_not_verified_message)

            }
        }
    )
}