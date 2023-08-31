package com.ykis.ykispam.pam.screens.add_apartment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.pam.screens.appartment.HomeUIState
import com.ykis.ykispam.R.string as AppText


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddApartmentScreen(
    homeUIState: HomeUIState,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    popUpScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ApartmentViewModel = hiltViewModel()
) {
    val secretKeyUiState = viewModel.secretKeyUiState

    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(PaddingValues(0.dp))
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        BasicToolbar(AppText.add_appartment, isFullScreen = true)
        Spacer(modifier = Modifier.smallSpacer())

        Card(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(20.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(PaddingValues(8.dp)),

                    ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_flat_secret_сode),
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = stringResource(id = R.string.tooltip_code),
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(PaddingValues(8.dp)),

                    ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp, end = 8.dp)
                    ) {
                        BasicField(
                            AppText.secret_сode,
                            AppText.secret_сode,
                            secretKeyUiState.secretCode,
                            viewModel::onSecretCodeChange,
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                    BasicImageButton(
                        AppText.add,
                        R.drawable.ic_stat_name,
                        modifier = Modifier
                    )
                    {
                        keyboard?.hide()
                        viewModel.onAddAppartmentClick(popUpScreen)
                    }

                }
            }
        }

    }

}

