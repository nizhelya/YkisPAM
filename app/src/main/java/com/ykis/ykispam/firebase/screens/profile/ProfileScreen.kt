package com.ykis.ykispam.firebase.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.spacer
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.screens.profile.components.ProfileTopBar
import com.ykis.ykispam.firebase.screens.profile.components.RevokeAccess

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    restartApp: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileTopBar(signOut = { viewModel.signOut(restartApp) },
            revokeAccess = { viewModel.revokeAccess() }
        )
        Spacer(modifier = Modifier.spacer())
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewModel.photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .width(96.dp)
                .height(96.dp)
        )
        Spacer(modifier = Modifier.spacer())
        if (viewModel.displayName.isNullOrEmpty()) {
            Text(
                text = "user",
                fontSize = 24.sp
            )
        } else {
            Text(
                text = viewModel.displayName,
                fontSize = 24.sp
            )
        }
        Text(
            text = viewModel.email,
            fontSize = 14.sp

        )
        Text(
            text = viewModel.providerId,
            fontSize = 14.sp

        )
        Text(
            text = viewModel.uid,
            fontSize = 14.sp

        )
    }
    RevokeAccess(
        restartApp = {
            viewModel.restartApp(restartApp)
        },
        showRevokedMessage = {
            SnackbarManager.showMessage(R.string.access_revoked_message)
        }

    )
}

