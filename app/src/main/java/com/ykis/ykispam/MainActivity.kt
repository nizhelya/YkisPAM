package com.ykis.ykispam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.theme.YkisPAMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val viewModel: ApartmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YkisPAMTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                YkisPamApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    homeUIState = uiState,
                )
            }
        }
    }

}

