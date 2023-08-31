package com.ykis.ykispam.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ExitScreen
import com.ykis.ykispam.SplashScreen
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.screens.add_apartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.HomeUIState


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    appState: YkisPamAppState,
    homeUIState: HomeUIState
) {

    composable(SPLASH_SCREEN) {
        SplashScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }

    composable(EXIT_SCREEN) {
        ExitScreen()
    }


    composable(PROFILE_SCREEN) {
        ProfileScreen(restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            openScreen = { route -> appState.navigate(route) },
        )
    }
    composable(VERIFY_EMAIL_SCREEN) {
        VerifyEmailScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            openScreen = { route -> appState.navigate(route) }
        )

    }
    composable(ADD_APARTMENT_SCREEN) {
        AddApartmentScreen(
            homeUIState = homeUIState,
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            popUpScreen = { appState.popUp() })
//        AddApartmentScreen(
//            homeUIState = homeUIState,
//            contentType = contentType,
//            navigationType = navigationType,
//            displayFeatures = displayFeatures,
//            popUpScreen = { appState.popUp() },
//        )

    }
//    composable(APARTMENT_SCREEN) {
//        ApartmentScreen(
//            homeUIState = homeUIState,
//            contentType = contentType,
//            navigationType = navigationType,
//            displayFeatures = displayFeatures,
//            openScreen = { route -> appState.navigate(route) },
//            restartApp = { route -> appState.clearAndNavigate(route) },
//        )
//    }
    composable(SETTINGS_SCREEN) {
        SettingsScreen(popUpScreen = { appState.popUp() })
    }

    composable(
        route = "$APARTMENT_SCREEN$APARTMENT_ID_ARG",
        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
    ) {
        ApartmentScreen(
            homeUIState = homeUIState,
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            openScreen = { route -> appState.navigate(route) },
            restartApp = { route -> appState.clearAndNavigate(route) },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
        )
    }
}






