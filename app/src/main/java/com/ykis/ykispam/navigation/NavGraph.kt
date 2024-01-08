package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.google.firestore.v1.FirestoreGrpc.SERVICE_NAME
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.SplashScreen
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.data.remote.api.ApiService.Companion.HOUSE_ID
import com.ykis.ykispam.pam.data.remote.api.ApiService.Companion.SERVICE
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.WaterScreen
import com.ykis.ykispam.pam.screens.bti.BtiScreen
import com.ykis.ykispam.pam.screens.family.FamilyScreen


fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    getApartment: (Int, ContentType) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}

) {

    composable(SPLASH_SCREEN) {
        SplashScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            isUserSignedOut = isUserSignedOut
        )
    }
    composable(
        route = "$BTI_SCREEN$FLAT_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            navArgument(ADDRESS) { defaultValue = ADDRESS_DEFAULT })
    )
    {
        BtiScreen(
            popUpScreen = { appState.popUp() },
            openScreen = { route -> appState.navigate(route) },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
            address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT,

            )
    }
    composable(
        route = "$FAMILY_SCREEN$FLAT_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            navArgument(ADDRESS) { defaultValue = ADDRESS_DEFAULT })
    )
    {
        FamilyScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
            address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT,

            )
    }
    composable(
        route = "$WATER_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
    ) {
        WaterScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
        )
    }

    composable(YkisRoute.ACCOUNT) {

        ProfileScreen(
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) },
        )
    }
    composable(YkisRoute.CHAT) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
        )
    }
    composable(YkisRoute.MESSAGE) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
        )
    }
    composable(YkisRoute.OSBB) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
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
            openScreen = { route -> appState.navigate(route) }
        )

    }
    composable(ADD_APARTMENT_SCREEN) {
        AddApartmentScreen(
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) },
        )

    }

    composable(YkisRoute.SETTINGS) {
        SettingsScreen(popUpScreen = { appState.popUp() })
    }

    composable(
        route = "$APARTMENT_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
        )
    ) {
        ApartmentScreen(
            openScreen = { route -> appState.navigate(route) },
            restartApp = { route -> appState.clearAndNavigate(route) },
            appState = appState,
            contentType = contentType,
            baseUIState = baseUIState,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            closeDetailScreen = closeDetailScreen,
            getApartments = getApartments,
            getApartment = getApartment,
            navigateToDetail = navigateToDetail,
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
            onDrawerClicked = onDrawerClicked,
        )
    }

}






