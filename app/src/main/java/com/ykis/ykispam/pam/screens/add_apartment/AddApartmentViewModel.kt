/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.pam.screens.add_apartment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.address.request.AddFlatByUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(
    private val addFlatByUser: AddFlatByUser,
    logService: LogService
) : BaseViewModel(logService) {

    private val _resultText = MutableLiveData<GetSimpleResponse>()
    val resultText: LiveData<GetSimpleResponse> = _resultText

    var uiState by mutableStateOf(SecretKeyUiState())
        private set
    private val secretCode
        get() = uiState.secretCode

    fun onSecretCodeChange(newValue: String) {
        uiState = uiState.copy(secretCode = newValue)
    }

    fun onAddAppartmentClick(openScreen: (String) -> Unit) {
        if (secretCode.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_field_error)
            return
        }

        addFlat(secretCode)
        if (failureData == null) {
            openScreen(APARTMENT_SCREEN)
        }

    }

    fun addFlat(secretCode: String) {
        addFlatByUser(secretCode) { it ->
            it.either(::handleFailure) {
                handleResultText(
                    it,
                    _resultText
                )
            }
        }
    }

    private fun handleResultText(
        result: GetSimpleResponse,
        livedata: MutableLiveData<GetSimpleResponse>
    ) {
        livedata.value = result
    }
}

