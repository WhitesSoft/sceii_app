package com.darksoft.sceii.ui.activities.login.model

import com.darksoft.sceii.domain.login.model.LoginModel

/*
* Estado de la pantalla UI
* */
sealed class LoginUIState {
    object Initial : LoginUIState()
    object Loading : LoginUIState()
    data class Success(val loginModel: LoginModel) : LoginUIState()
    data class Error(val msg: String) : LoginUIState()
}