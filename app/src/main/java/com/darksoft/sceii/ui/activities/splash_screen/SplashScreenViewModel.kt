package com.darksoft.sceii.ui.activities.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darksoft.sceii.core.constants.USER_DATA_KEY
import com.darksoft.sceii.domain.preferences.GetDataUserUseCase
import com.darksoft.sceii.ui.activities.splash_screen.model.SplashScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getDataUserUseCase: GetDataUserUseCase
) : ViewModel() {

    // Creamos el estado de la pantalla
    private val _uiState = MutableStateFlow<SplashScreenUIState>(SplashScreenUIState.Initial) // Estado inicial
    val uiState: StateFlow<SplashScreenUIState> = _uiState // El usuario solo vera el estado de la pantalla

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val userData = getDataUserUseCase.invoke(USER_DATA_KEY)
                _uiState.value = SplashScreenUIState.Success(userData!!)
            } catch (e: Exception) {
                _uiState.value = SplashScreenUIState.Error("No se encontro el usuario")
            }
        }

    }

}