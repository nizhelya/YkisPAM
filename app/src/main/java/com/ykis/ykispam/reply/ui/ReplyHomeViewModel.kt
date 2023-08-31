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

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.screens.appartment.HomeUIState
import com.ykis.ykispam.reply.data.Email
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReplyHomeViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    logService: LogService
) : BaseViewModel(logService) {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    val uiState: StateFlow<HomeUIState> = _uiState
    private val isEmailVerified get() = firebaseService.currentUser?.isEmailVerified ?: false

    init {
        getAuthState()
    }

    fun getAuthState() = firebaseService.getAuthState(viewModelScope)


    data class ReplyHomeUIState(
        val emails: List<Email> = emptyList(),
        val selectedEmail: Email? = null,
        val isDetailOnlyOpen: Boolean = false,
        val loading: Boolean = false,
        val error: String? = null
    )
}