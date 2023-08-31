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
//@Composable
//fun ReplyInboxScreen(
//    contentType: ContentType,
//    replyHomeUIState: HomeUIState,
//    navigationType: NavigationType,
//    displayFeatures: List<DisplayFeature>,
//    closeDetailScreen: () -> Unit,
//    navigateToDetail: (Long, ContentType) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    /**
//     * При переходе со страницы LIST_AND_DETAIL
//     * на страницу LIST отмените выбор, и пользователь должен увидеть экран LIST.     */
//    LaunchedEffect(key1 = contentType) {
//        if (contentType == ContentType.SINGLE_PANE && !replyHomeUIState.isDetailOnlyOpen) {
//            closeDetailScreen()
//        }
//    }
//
//    val emailLazyListState = rememberLazyListState()
//
//    if (contentType == ContentType.DUAL_PANE) {
//        TwoPane(
//            first = {
//                ReplyEmailList(
//                    emails = replyHomeUIState.emails,
//                    emailLazyListState = emailLazyListState,
//                    navigateToDetail = navigateToDetail
//                )
//            },
//            second = {
//                ReplyEmailDetail(
//                    email = replyHomeUIState.selectedEmail ?: replyHomeUIState.emails.first(),
//                    isFullScreen = false
//                )
//            },
//            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
//            displayFeatures = displayFeatures
//        )
//    } else {
//        Box(modifier = modifier.fillMaxSize()) {
//            ReplySinglePaneContent(
//                replyHomeUIState = replyHomeUIState,
//                emailLazyListState = emailLazyListState,
//                modifier = Modifier.fillMaxSize(),
//                closeDetailScreen = closeDetailScreen,
//                navigateToDetail = navigateToDetail
//            )
//            // When we have bottom navigation we show FAB at the bottom end.
//            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
//                LargeFloatingActionButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(16.dp),
//                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = stringResource(id = R.string.edit),
//                        modifier = Modifier.size(28.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ReplySinglePaneContent(
//    replyHomeUIState: HomeUIState,
//    emailLazyListState: LazyListState,
//    modifier: Modifier = Modifier,
//    closeDetailScreen: () -> Unit,
//    navigateToDetail: (Long, ContentType) -> Unit
//) {
//    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
//        BackHandler {
//            closeDetailScreen()
//        }
//        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) {
//            closeDetailScreen()
//        }
//    } else {
//        ReplyEmailList(
//            emails = replyHomeUIState.emails,
//            emailLazyListState = emailLazyListState,
//            modifier = modifier,
//            navigateToDetail = navigateToDetail
//        )
//    }
//}
//
//@Composable
//fun ReplyEmailList(
//    emails: List<Email>,
//    emailLazyListState: LazyListState,
//    modifier: Modifier = Modifier,
//    navigateToDetail: (Long, ContentType) -> Unit
//) {
//    LazyColumn(modifier = modifier, state = emailLazyListState) {
//        item {
//            ReplySearchBar(modifier = Modifier.fillMaxWidth())
//        }
//        items(items = emails, key = { it.id }) { email ->
//            ReplyEmailListItem(email = email) { emailId ->
//                navigateToDetail(emailId, ContentType.SINGLE_PANE)
//            }
//        }
//    }
//}
//
//@Composable
//fun ReplyEmailDetail(
//    email: Email,
//    isFullScreen: Boolean = true,
//    modifier: Modifier = Modifier.fillMaxSize(),
//    onBackPressed: () -> Unit = {}
//) {
//    LazyColumn(
//        modifier = modifier
//            .background(MaterialTheme.colorScheme.inverseOnSurface)
//            .padding(top = 16.dp)
//    ) {
//        item {
//            EmailDetailAppBar(email, isFullScreen) {
//                onBackPressed()
//            }
//        }
//        items(items = email.threads, key = { it.id }) { email ->
//            ReplyEmailThreadItem(email = email)
//        }
//    }
//}
