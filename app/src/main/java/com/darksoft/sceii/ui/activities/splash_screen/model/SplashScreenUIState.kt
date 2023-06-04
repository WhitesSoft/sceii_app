package com.darksoft.sceii.ui.activities.splash_screen.model

import com.darksoft.sceii.domain.login.model.LoginModel
import com.darksoft.sceii.ui.activities.login.model.LoginUIState

sealed class SplashScreenUIState {
    object Initial : SplashScreenUIState()
    data class Success(val loginModel: LoginModel) : SplashScreenUIState()
    data class Error(val msg: String) : SplashScreenUIState()
}