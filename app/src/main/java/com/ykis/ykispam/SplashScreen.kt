package com.ykis.ykispam

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.composable.BasicButton
import com.ykis.ykispam.core.ext.basicButton
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import kotlinx.coroutines.delay
import com.ykis.ykispam.R.string as AppText

private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashScreen(
//    contentType: ContentType,
//    homeUIState: HomeUIState,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isUserSignedOut = viewModel.getAuthState().collectAsState().value

    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Text(text = stringResource(AppText.generic_error))
            BasicButton(AppText.try_again, Modifier.basicButton()) {
                viewModel.onAppStart(isUserSignedOut,restartApp)
            }
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart(isUserSignedOut,restartApp)
    }
}