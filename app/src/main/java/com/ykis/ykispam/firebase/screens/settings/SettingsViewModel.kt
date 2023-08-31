package com.ykis.ykispam.firebase.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.core.ext.isValidEmail
import com.ykis.ykispam.core.ext.isValidPassword
import com.ykis.ykispam.core.ext.passwordMatches
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.firebase.model.service.repo.SendEmailVerificationResponse
import com.ykis.ykispam.firebase.model.service.repo.SignUpResponse
import com.ykis.ykispam.firebase.screens.sign_up.components.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val configurationService: ConfigurationService,
    logService: LogService
) : BaseViewModel(logService) {

    private val _isSwitchOn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isSwitchOn = _isSwitchOn.asStateFlow()

    private val _textPreference: MutableStateFlow<String> = MutableStateFlow("")
    var textPreference = _textPreference.asStateFlow()

    private val _intPreference: MutableStateFlow<Int> = MutableStateFlow(0)
    var intPreference = _intPreference.asStateFlow()


    fun toggleSwitch(){
        _isSwitchOn.value = _isSwitchOn.value.not()
        // here is place for permanent storage handling - switch
    }

    fun saveText(finalText: String) {
        _textPreference.value = finalText
        // place to store text
    }

    // just checking, if it is not empty - but you can check anything
    fun checkTextInput(text: String) = text.isNotEmpty()
    private val separatorChar = DecimalFormatSymbols.getInstance(Locale.ENGLISH).decimalSeparator

    // filtering only numbers and decimal separator
    fun filterNumbers(text: String): String = text.filter { it.isDigit() || it == separatorChar}

    // someone can still put more decimal points into the textfield
    // we should always try to convert text to number
    fun checkNumber(text: String): Boolean {
        val value = text.toDoubleOrNull() ?: return false
        return value < 0
    }

    // saving the number / show error if something goes wrong
    fun saveNumber(text: String) {
        val value = text.toDoubleOrNull() ?: 0 // default value / handle the error in some way - show toast or something

    }
    companion object {
        const val TAG = "SettingsViewModel"
    }

//
//    val options = mutableStateOf<List<String>>(listOf())
//
//    val wifi by mutableStateOf(configurationService.isWiFiCheckConfig)
//    val mpbile by mutableStateOf(configurationService.isMobileCheckConfig)
//
////    fun loadTaskOptions() {
////        val hasEditOption = configurationService.isShowTaskEditButtonConfig
////        options.value = TaskActionOption.getOptions(hasEditOption)
////    }
//
//
//
//    init {
//        launchCatching { configurationService.fetchConfiguration() }
//    }
//
//
//
//
//    var uiState = mutableStateOf(SettingsUiState())
//        private set
//
//    private val email
//        get() = uiState.value.wifi
//    private val password
//        get() = uiState.value.mobile
//
//
//    fun onWiFiChange(newValue: Boolean) {
//        uiState.value = uiState.value.copy(wifi  = newValue)
//    }
//    fun onMobileChange(newValue: Boolean) {
//        uiState.value = uiState.value.copy(mobile  = newValue)
//    }
//


//    fun signUpWithEmailAndPassword() {
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(R.string.email_error)
//            return
//        }
//
//        if (!password.isValidPassword()) {
//            SnackbarManager.showMessage(R.string.password_error)
//            return
//        }
//
//        if (!password.passwordMatches(uiState.value.repeatPassword)) {
//            SnackbarManager.showMessage(R.string.password_match_error)
//            return
//        }
//
//        launchCatching {
//            signUpResponse = Response.Loading
//            signUpResponse = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
//        }
//    }

//
//
    fun onConfirmClick(popUpScreen: () -> Unit) {
        launchCatching {
//            signUpResponse = Response.Loading
//            signUpResponse = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
        }
        popUpScreen()
    }
//
//    fun showVerifyEmailMessage() {
//        SnackbarManager.showMessage(R.string.verify_email_message)
//
//    }
}