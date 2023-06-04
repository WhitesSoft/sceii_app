package com.darksoft.sceii.ui.activities.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.darksoft.sceii.R
import com.darksoft.sceii.databinding.ActivityLoginBinding
import com.darksoft.sceii.ui.MainActivity
import com.darksoft.sceii.ui.activities.login.model.LoginUIState
import com.darksoft.sceii.ui.activities.splash_screen.model.SplashScreenUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    @SuppressLint("ResourceType", "UseCompatLoadingForColorStateLists")
    private fun initUI() {
        // Establecemos el color de los bordes de los TextInputLayout
        binding.tilPassword.setBoxStrokeColorStateList(
            resources.getColorStateList
                (R.drawable.selector_outline, null)
        )
        binding.tilUsuario.setBoxStrokeColorStateList(
            resources.getColorStateList
                (R.drawable.selector_outline, null)
        )

        // Iniciar sesion
        binding.btnIniciarSesion.setOnClickListener {
            iniciarSesion()
        }

    }

    private fun iniciarSesion() {
        val ru = binding.tilUsuario.editText?.text.toString()
        val clave = binding.tilPassword.editText?.text.toString()

        if (ru.isNotEmpty() || clave.isNotEmpty()) {

            viewModel.login(ru, clave)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState.collect { uiState ->
                        when (uiState) {
                            is LoginUIState.Loading -> {
                                binding.loading.isVisible = true
                                binding.btnIniciarSesion.isVisible = false
                            }
                            is LoginUIState.Success -> {
                                // Mostrar la informacion
                                binding.loading.isVisible = false
                                binding.btnIniciarSesion.isVisible = true
                                Log.i("token", "token: ${uiState.loginModel.toString()} ")

                                // Guardar datos en dataStore
                                viewModel.saveUserData(uiState.loginModel)

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            is LoginUIState.Error -> {
                                binding.loading.isVisible = false
                                binding.btnIniciarSesion.isVisible = true
                                Log.i("token", "error: ${uiState.msg}")
                            }
                            else -> {
                                binding.loading.isVisible = false
                                Log.i("token", "else")
                            }
                        }
                    }
                }

            }

        } else {
            binding.tilUsuario.error = "Ingrese su RU"
            binding.tilPassword.error = "Ingrese su contraseña"
        }

    }
}