package com.ykis.ykispam

import android.content.res.Resources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.navigation.ApartmentNavigationRail
import com.ykis.ykispam.navigation.BottomNavigationBar
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.DevicePosture
import com.ykis.ykispam.navigation.ModalNavigationDrawerContent
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.NavigationContentPosition
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.navigation.PermanentNavigationDrawerContent
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.navigation.YkisPamGraph
import com.ykis.ykispam.navigation.isBookPosture
import com.ykis.ykispam.navigation.isSeparating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun YkisPamApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    isUserSignedOut:Boolean,
    getApartments: () -> Unit ,
    closeDetailScreen: () -> Unit = {},
    getApartment: (Int, ContentType) -> Unit = { _, _ -> },
    navigateToDetail: (ContentDetail, ContentType) -> Unit = { _, _ -> }

) {
    val navigationType: NavigationType
    val contentType: ContentType
    val appState = rememberAppState()
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE

        }

        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            NavigationContentPosition.TOP
        }

        else -> {
            NavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType, //(BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER)
        contentType = contentType,      //(SINGLE,DUAL)
        displayFeatures = displayFeatures,  //fold device развернуто BookPosture или сложено  SINGLE
        navigationContentPosition = navigationContentPosition, // PERMANENT_NAVIGATION_DRAWER содержимое TOP or CENTER
        appState = appState,
        baseUIState = baseUIState,
        isUserSignedOut = isUserSignedOut,
        getApartments = getApartments,
        closeDetailScreen = closeDetailScreen,
        getApartment = getApartment,
        navigateToDetail = navigateToDetail

    )
}

@Composable
fun NavigationWrapper(
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    getApartment: (Int, ContentType) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit

) {
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = appState.coroutineScope
    val navController = appState.navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination

    if (isUserSignedOut) {
        AppContent(
            appState = appState,
            baseUIState = baseUIState,
            isUserSignedOut = isUserSignedOut,
            navigationType = navigationType,
            contentType = contentType,
            displayFeatures = displayFeatures,
            navigationContentPosition = navigationContentPosition,
            navController = navController,
            selectedDestination = selectedDestination,
            navigateToDestination = appState::navigateTo,
            getApartments = getApartments,
            closeDetailScreen = closeDetailScreen,
            getApartment = getApartment,
            navigateToDetail = navigateToDetail

        )
    } else {


        if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
            PermanentNavigationDrawer(drawerContent = {
                PermanentNavigationDrawerContent(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToDestination = appState::navigateTo
                )
            }) {
                AppContent(
                    appState = appState,
                    baseUIState = baseUIState,
                    isUserSignedOut = isUserSignedOut,

                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToDestination = appState::navigateTo,
                    getApartments = getApartments,
                    closeDetailScreen = closeDetailScreen,
                    getApartment = getApartment,
                    navigateToDetail = navigateToDetail

                )
            }
        } else {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        baseUIState = baseUIState,
                        selectedDestination = selectedDestination,
                        navigationContentPosition = navigationContentPosition,
                        navigateToDestination = appState::navigateTo,
                        onDrawerClicked = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                    )
                },
                drawerState = drawerState
            ) {
                AppContent(

                    appState = appState,
                    baseUIState = baseUIState,
                    isUserSignedOut = isUserSignedOut,

                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToDestination = appState::navigateTo,
                    getApartments = getApartments,
                    closeDetailScreen = closeDetailScreen,
                    getApartment = getApartment,
                    navigateToDetail = navigateToDetail

                ) {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            }
        }
    }
}


@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    getApartment: (Int, ContentType) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData,
                    )
                }
            )
        },
    ) {
        Row(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL && !isUserSignedOut)
            {
                ApartmentNavigationRail(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToDestination = appState::navigateTo,
                    onDrawerClicked = onDrawerClicked,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)

            ) {
                YkisNavHost(
                    modifier = Modifier.weight(1f),
                    appState = appState,
                    baseUIState = baseUIState,
                    isUserSignedOut = isUserSignedOut,
                    navController = navController,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationType = navigationType,
                    getApartments = getApartments,
                    closeDetailScreen = closeDetailScreen,
                    navigateToDestination = navigateToDestination,
                    getApartment = getApartment,
                    navigateToDetail = navigateToDetail,
                    onDrawerClicked = onDrawerClicked
                )
                AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    BottomNavigationBar(
                        navigateToDestination = navigateToDestination,
                        selectedDestination = selectedDestination,
                    )
                }
            }
        }
    }
}

@Composable
private fun YkisNavHost(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    getApartment: (Int, ContentType) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}

) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SPLASH_SCREEN,
    ) {
        YkisPamGraph(
            contentType,
            navigationType,
            displayFeatures,
            appState,
            baseUIState,
            isUserSignedOut,
            getApartments,
            closeDetailScreen,
            navigateToDestination,
            getApartment,
            navigateToDetail,
            onDrawerClicked
        )
    }
}


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(navController, snackbarManager, resources, coroutineScope) {
        YkisPamAppState(
            navController,
            snackbarHostState,
            snackbarManager,
            resources,
            coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}