/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ykis.ykispam.reply.ui

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReplyApp(
//    windowSize: WindowSizeClass,
//    displayFeatures: List<DisplayFeature>,
//    replyHomeUIState: HomeUIState,
//    closeDetailScreen: () -> Unit = {},
//    navigateToDetail: (Long, ContentType) -> Unit = { _, _ -> }
//) {
//    /**
//     * Содержимое внутри навигационной направляющей/ящика также можно расположить сверху,
//     * снизу или по центру (TOP, CENTER) для эргономичность и доступность в зависимости от высоты устройства.
//     */
//    val navigationType: NavigationType
//    val contentType: ContentType
//
//    /**
//     * We are using display's folding features to map the device postures a fold is in.
//     * In the state of folding device If it's half fold in BookPosture we want to avoid content
//     * at the crease/hinge
//     */
//    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
//
//    val foldingDevicePosture = when {
//        isBookPosture(foldingFeature) ->
//            DevicePosture.BookPosture(foldingFeature.bounds)
//
//        isSeparating(foldingFeature) ->
//            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
//
//        else -> DevicePosture.NormalPosture
//    }
//
//    when (windowSize.widthSizeClass) {
//        WindowWidthSizeClass.Compact -> {
//            navigationType = NavigationType.BOTTOM_NAVIGATION
//            contentType = ContentType.SINGLE_PANE
//        }
//        WindowWidthSizeClass.Medium -> {
//            navigationType = NavigationType.NAVIGATION_RAIL
//            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
//                ContentType.DUAL_PANE
//            } else {
//                ContentType.SINGLE_PANE
//            }
//        }
//        WindowWidthSizeClass.Expanded -> {
//            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
//                NavigationType.NAVIGATION_RAIL
//            } else {
//                NavigationType.PERMANENT_NAVIGATION_DRAWER
//            }
//            contentType = ContentType.DUAL_PANE
//        }
//        else -> {
//            navigationType = NavigationType.BOTTOM_NAVIGATION
//            contentType = ContentType.SINGLE_PANE
//        }
//    }
//
//    /**
//     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
//     * ergonomics and reachability depending upon the height of the device.
//     */
//    val navigationContentPosition = when (windowSize.heightSizeClass) {
//        WindowHeightSizeClass.Compact -> {
//            ReplyNavigationContentPosition.TOP
//        }
//        WindowHeightSizeClass.Medium,
//        WindowHeightSizeClass.Expanded -> {
//            ReplyNavigationContentPosition.CENTER
//        }
//        else -> {
//            ReplyNavigationContentPosition.TOP
//        }
//    }
//
//    ReplyNavigationWrapper(
//        navigationType = navigationType,
//        contentType = contentType,
//        displayFeatures = displayFeatures,
//        navigationContentPosition = navigationContentPosition,
//        replyHomeUIState = replyHomeUIState,
//        closeDetailScreen = closeDetailScreen,
//        navigateToDetail = navigateToDetail
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun ReplyNavigationWrapper(
//    navigationType: ReplyNavigationType,
//    contentType: ReplyContentType,
//    displayFeatures: List<DisplayFeature>,
//    navigationContentPosition: ReplyNavigationContentPosition,
//    replyHomeUIState: ReplyHomeUIState,
//    closeDetailScreen: () -> Unit,
//    navigateToDetail: (Long, ReplyContentType) -> Unit
//) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    val navController = rememberNavController()
//    val navigationActions = remember(navController) {
//        NavigationActions(navController)
//    }
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val selectedDestination =
//        navBackStackEntry?.destination?.route ?: ReplyRoute.INBOX
//
//    if (navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER) {
//        // TODO check on custom width of PermanentNavigationDrawer: b/232495216
//        PermanentNavigationDrawer(drawerContent = {
//            PermanentNavigationDrawerContent(
//                selectedDestination = selectedDestination,
//                navigationContentPosition = navigationContentPosition,
//                navigateToTopLevelDestination = navigationActions::navigateTo,
//            )
//        }) {
//            ReplyAppContent(
//                navigationType = navigationType,
//                contentType = contentType,
//                displayFeatures = displayFeatures,
//                navigationContentPosition = navigationContentPosition,
//                replyHomeUIState = replyHomeUIState,
//                navController = navController,
//                selectedDestination = selectedDestination,
//                navigateToTopLevelDestination = navigationActions::navigateTo,
//                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail
//            )
//        }
//    } else {
//        ModalNavigationDrawer(
//            drawerContent = {
//                ModalNavigationDrawerContent(
//                    selectedDestination = selectedDestination,
//                    navigationContentPosition = navigationContentPosition,
//                    navigateToTopLevelDestination = navigationActions::navigateTo,
//                    onDrawerClicked = {
//                        scope.launch {
//                            drawerState.close()
//                        }
//                    }
//                )
//            },
//            drawerState = drawerState
//        ) {
//            ReplyAppContent(
//                navigationType = navigationType,
//                contentType = contentType,
//                displayFeatures = displayFeatures,
//                navigationContentPosition = navigationContentPosition,
//                replyHomeUIState = replyHomeUIState,
//                navController = navController,
//                selectedDestination = selectedDestination,
//                navigateToTopLevelDestination = navigationActions::navigateTo,
//                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail
//            ) {
//                scope.launch {
//                    drawerState.open()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ReplyAppContent(
//    modifier: Modifier = Modifier,
//    navigationType: ReplyNavigationType,
//    contentType: ReplyContentType,
//    displayFeatures: List<DisplayFeature>,
//    navigationContentPosition: ReplyNavigationContentPosition,
//    replyHomeUIState: ReplyHomeUIState,
//    navController: NavHostController,
//    selectedDestination: String,
//    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
//    closeDetailScreen: () -> Unit,
//    navigateToDetail: (Long, ReplyContentType) -> Unit,
//    onDrawerClicked: () -> Unit = {}
//) {
//    Row(modifier = modifier.fillMaxSize()) {
//        AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
//            ReplyNavigationRail(
//                selectedDestination = selectedDestination,
//                navigationContentPosition = navigationContentPosition,
//                navigateToTopLevelDestination = navigateToTopLevelDestination,
//                onDrawerClicked = onDrawerClicked,
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.inverseOnSurface)
//        ) {
//            ReplyNavHost(
//                navController = navController,
//                contentType = contentType,
//                displayFeatures = displayFeatures,
//                replyHomeUIState = replyHomeUIState,
//                navigationType = navigationType,
//                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail,
//                modifier = Modifier.weight(1f),
//            )
//            AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
//                ReplyBottomNavigationBar(
//                    selectedDestination = selectedDestination,
//                    navigateToTopLevelDestination = navigateToTopLevelDestination
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun ReplyNavHost(
//    navController: NavHostController,
//    contentType: ReplyContentType,
//    displayFeatures: List<DisplayFeature>,
//    replyHomeUIState: ReplyHomeUIState,
//    navigationType: ReplyNavigationType,
//    closeDetailScreen: () -> Unit,
//    navigateToDetail: (Long, ReplyContentType) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    NavHost(
//        modifier = modifier,
//        navController = navController,
//        startDestination = ReplyRoute.INBOX,
//    ) {
//        composable(ReplyRoute.INBOX) {
//            ReplyInboxScreen(
//                contentType = contentType,
//                replyHomeUIState = replyHomeUIState,
//                navigationType = navigationType,
//                displayFeatures = displayFeatures,
//                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail,
//            )
//        }
//        composable(ReplyRoute.DM) {
//            EmptyComingSoon()
//        }
//        composable(ReplyRoute.ARTICLES) {
//            EmptyComingSoon()
//        }
//        composable(ReplyRoute.GROUPS) {
//            EmptyComingSoon()
//        }
//    }
//}
