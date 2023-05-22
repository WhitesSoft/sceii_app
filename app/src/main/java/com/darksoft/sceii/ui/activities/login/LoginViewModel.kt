package com.darksoft.sceii.ui.activities.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darksoft.sceii.core.network.ResultType
import com.darksoft.sceii.domain.login.PostLoginUseCase
import com.darksoft.sceii.domain.login.dto.LoginDto
import com.darksoft.sceii.ui.activities.login.model.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: PostLoginUseCase):
    ViewModel(){

        // Creamos el estado de la pantalla
        private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Initial) // Estado inicial
        val uiState: StateFlow<LoginUIState> = _uiState  // El usuario solo vera el estado de la pantalla

        fun login(ru: String, clave: String) {
            viewModelScope.launch(Dispatchers.IO) {
                // Estado de loading
                _uiState.value = LoginUIState.Loading
                when(val response = loginUseCase(loginDto = LoginDto(ru, clave))) {
                    is ResultType.Error -> _uiState.value = LoginUIState.Error("Error")
                    is ResultType.Success -> _uiState.value = LoginUIState.Success(response.data!!)
                }
            }
        }

    }