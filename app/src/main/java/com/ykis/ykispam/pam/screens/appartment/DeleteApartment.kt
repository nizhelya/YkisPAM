package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.core.composable.InfoCardEditor
import com.ykis.ykispam.core.ext.card

@Composable
fun DeleteAppartment(address:String,deleteMyAppartment: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    InfoCardEditor(
        R.string.delete_appartment,
        R.drawable.ic_delete_my_account,
        "",
        modifier = Modifier.card()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.title_delete_appartment)) },
            text = { Text(stringResource(R.string.desc_delete_appartment,address)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.title_delete_appartment) {
                    deleteMyAppartment()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}