package com.ykis.ykispam.firebase.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.ActionToolbar
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.core.ext.toolbarActions
import com.ykis.ykispam.R.drawable as AppIcon
import com.ykis.ykispam.R.string as AppText
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    popUpScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
//    val uiState by viewModel.uiState
//    val keyboard = LocalSoftwareKeyboardController.current
//    var checkedState by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ActionToolbar(
                title = R.string.settings,
                endActionIcon =R.drawable.ic_check ,
                modifier = Modifier, isFullScreen =true ,
               endAction = { viewModel.onConfirmClick(popUpScreen) }

            )

        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp),

        ) {
//            SettingsClickable(
//                name = R.string.title,
//                icon = R.drawable.ic_exit,
//                iconDesc = R.string.sign_in,
//            ) {
//                // here you can do anything - navigate - open other settings, ...
//            }
            SettingsGroup(name = R.string.settings_loadin_from_server) {

                SettingsSwitch(
                    name = R.string.loading_from_wifi,
                    icon = R.drawable.ic_loading_wifi,
                    iconDesc = R.string.loading_from_wifi,
                    // value is collected from StateFlow - updates the UI on change
                    state = viewModel.isSwitchOn.collectAsState()
                ) {
                    // call ViewModel to toggle the value
                    viewModel.toggleSwitch()
                }
                SettingsSwitch(
                    name = R.string.loading_from_mobile,
                    icon = R.drawable.ic_loading_mobile,
                    iconDesc = R.string.loading_from_mobile,
                    // value is collected from StateFlow - updates the UI on change
                    state = viewModel.isSwitchOn.collectAsState()
                ) {
                    // call ViewModel to toggle the value
                    viewModel.toggleSwitch()
                }
            }
//            SettingsText(
//                name = R.string.title,
//                icon = R.drawable.ic_create_account,
//                iconDesc = R.string.contacts,
//                state = viewModel.textPreference.collectAsState(),
//                onSave = { finalText -> viewModel.saveText(finalText) },
//                onCheck = { text -> viewModel.checkTextInput(text) },
//            )
//            SettingsNumber(
//                name = R.string.title,
//                icon = R.drawable.ic_calendar,
//                iconDesc = R.string.email_colon,
//                state = viewModel.textPreference.collectAsState(),
//                inputFilter = {text -> viewModel.filterNumbers(text)},
//                        onSave = { finalText -> viewModel.saveNumber(finalText) },
//                onCheck = { text -> viewModel.checkNumber(text) },
//            )
        }
    }
}
//    Column(
//        modifier = Modifier
//            .padding(PaddingValues(0.dp))
//            .fillMaxWidth()
//            .fillMaxHeight()
//
//    ) {
//            ActionToolbar(
//                title = AppText.sign_out,
//                modifier = Modifier.toolbarActions(),
//                endActionIcon = AppIcon.ic_exit,
//                isFullScreen = true,
//                endAction = { viewModel.onConfirmClick(popUpScreen) }
//            )
//        Spacer(modifier = Modifier.smallSpacer())
//
//        Card(
//            modifier = Modifier.padding(PaddingValues(8.dp)),
//            backgroundColor = MaterialTheme.colors.background,
//            elevation = 20.dp,
//            shape = RoundedCornerShape(10.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.padding(PaddingValues(8.dp)),
//
//                    ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.add_flat_secret_сode),
//                            modifier = Modifier.padding(4.dp),
//                            textAlign = TextAlign.Left,
//                            style = MaterialTheme.typography.h6
//                        )
//                        Text(
//                            text = stringResource(id = R.string.tooltip_code),
//                            modifier = Modifier.padding(4.dp),
//                            textAlign = TextAlign.Left,
//                            style = MaterialTheme.typography.body2
//                        )
//                    }
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.padding(PaddingValues(8.dp)),
//
//                    ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 4.dp, end = 8.dp)
//                    ) {
//
//                    }
//                    BasicImageButton(
//                        R.string.add,
//                        R.drawable.ic_stat_name,
//                        modifier = Modifier
//                    )
//                    {
//                        keyboard?.hide()
//                    }
//
//                }
//            }
//        }
//
//    }

