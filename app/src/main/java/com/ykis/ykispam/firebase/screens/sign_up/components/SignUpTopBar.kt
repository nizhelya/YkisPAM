package com.ykis.ykispam.firebase.screens.sign_up.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.ykis.ykispam.core.composable.BackIcon
import com.ykis.ykispam.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),


        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(AppText.create_account_sign),
//                    style = MaterialTheme.typography.titleLarge,
                )
            }

        },

        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}