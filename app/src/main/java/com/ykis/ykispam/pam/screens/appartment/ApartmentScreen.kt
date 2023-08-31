package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.NavigationType
import kotlinx.coroutines.delay
import com.ykis.ykispam.R.drawable as AppIcon
import com.ykis.ykispam.R.string as AppText

private const val LOAD_TIMEOUT = 1000L

@Composable
fun ApartmentScreen(
    homeUIState: HomeUIState,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
    addressId: Comparable<*>,
    modifier: Modifier = Modifier,
    viewModel: ApartmentViewModel = hiltViewModel()
) {


    CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)

    LaunchedEffect(viewModel) {
        delay(LOAD_TIMEOUT)
        viewModel.initialize()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
//                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = modifier.padding(16.dp)
            ) {
                //                Icon(Icons.Filled.Add, "Add")
                Image(
                    painter = painterResource(id = AppIcon.ic_stat_name),
                    contentDescription = stringResource(id = AppText.add_appartment)
                )
            }
        }
    ) {
        val apartments by viewModel.apartments.observeAsState(emptyList())
        val apartmentLazyListState = rememberLazyListState()
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ApartmentTopBar(exitApp = { viewModel.onExitAppClick(restartApp) },
                openScreen = { viewModel.onSettingsClick(openScreen) }
            )
            Spacer(modifier = Modifier.smallSpacer())
            if (apartments.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(PaddingValues(20.dp))
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = AppText.add_appartment_info),
                            modifier = Modifier.padding(PaddingValues(8.dp)),
                            textAlign = TextAlign.Center,
                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = AppText.no_data_subtitle),
                            modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            } else {
                Box(modifier = modifier.fillMaxSize()) {
                    ApartmentList(
                        apartments = apartments,
                        popUpScreean = {},
                        apartmentLazyListState = apartmentLazyListState,
                        modifier = modifier,
                    )
                }

            }
        }
    }
}



