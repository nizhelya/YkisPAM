package com.ykis.ykispam.ui.screens.appbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.DangerousCardEditor
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.core.composable.RegularCardEditor
import com.ykis.ykispam.core.ext.card
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.navigation.NavigationType
import kotlinx.coroutines.launch
import com.ykis.ykispam.R.string as AppText

@Composable
private fun ExitApp(exitApp: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(AppText.sign_out, R.drawable.ic_exit, "", Modifier.card()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    exitApp()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}


@Composable
private fun OpenSettingsScreen(openSettingsScreen: () -> Unit) {

    DangerousCardEditor(
        AppText.settings,
        R.drawable.ic_settings,
        "",
        Modifier.card()
    ) {
        openSettingsScreen()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApartmentTopAppBar(
    appState: YkisPamAppState,
    apartment: ApartmentEntity,
    isButtonAction: Boolean,
    modifier: Modifier = Modifier,
    onButtonAction: () -> Unit,
    onButtonPressed: () -> Unit,
    navigationType: NavigationType

    ) {
    var openMenu by remember { mutableStateOf(false) }
    var showWarningDialog by remember { mutableStateOf(false) }
    if (showWarningDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            title = { Text(stringResource(R.string.title_delete_appartment)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.title_delete_appartment) {
                    onButtonAction()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            title = {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                            text = apartment.address,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = stringResource(
                                id = R.string.response_address_id,
                                apartment.addressId.toString()
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
            },
            navigationIcon = {
                if(navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    IconButton(onClick = {
                        appState.coroutineScope.launch {
                            onButtonPressed()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.driver_menu),
                        )
                    }
                }
            },
            actions = {
                if (isButtonAction) {
                    IconButton(
                        onClick = {
                            appState.coroutineScope.launch {
                                showWarningDialog = true

                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_appartment),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

        )
}
