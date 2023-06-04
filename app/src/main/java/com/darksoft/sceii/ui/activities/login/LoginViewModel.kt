package com.darksoft.sceii.ui.activities.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darksoft.sceii.core.constants.USER_DATA_KEY
import com.darksoft.sceii.core.network.ResultType
import com.darksoft.sceii.domain.login.PostLoginUseCase
import com.darksoft.sceii.domain.login.dto.LoginDto
import com.darksoft.sceii.domain.login.model.LoginModel
import com.darksoft.sceii.domain.preferences.SaveDataUserUseCase
import com.darksoft.sceii.ui.activities.login.model.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase,
    private val saveDataUserUseCase: SaveDataUserUseCase
) : ViewModel() {

    // Creamos el estado de la pantalla
    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Initial) // Estado inicial
    val uiState: StateFlow<LoginUIState> =
        _uiState  // El usuario solo vera el estado de la pantalla

    fun login(ru: String, clave: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Estado de loading
            _uiState.value = LoginUIState.Loading
            when (val response = loginUseCase(loginDto = LoginDto(ru, clave))) {
                is ResultType.Error -> _uiState.value = LoginUIState.Error("Error")
                is ResultType.Success -> _uiState.value = LoginUIState.Success(response.data!!)
            }
        }
    }

    fun saveUserData(loginModel: LoginModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveDataUserUseCase(USER_DATA_KEY, loginModel)
                Log.i("userdata", "Datos del usuario guardados correctamente")
            } catch (e: Exception) {
                Log.i("userdata", "Error al guardar los datos del usuario")
            }
        }
    }

}