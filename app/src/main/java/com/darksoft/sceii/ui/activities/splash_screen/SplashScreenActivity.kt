package com.darksoft.sceii.ui.activities.splash_screen

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.darksoft.sceii.databinding.ActivitySplashScreenBinding
import com.darksoft.sceii.ui.MainActivity
import com.darksoft.sceii.ui.activities.login.LoginActivity
import com.darksoft.sceii.ui.activities.splash_screen.model.SplashScreenUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashScreenViewModel>()
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Llamamos al metodo que obtiene los datos del usuario
        viewModel.getUserData()

        val handler = Looper.myLooper()?.let { Handler(it) }
        handler?.postDelayed({

            lifecycleScope.launch {
                // Corrutina para observar los cambios en el estado de la UI
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { uiState ->
                        when (uiState) {
                            is SplashScreenUIState.Success -> {
                                // El usuario está logueado (24 horas)
                                Log.i("user", "user: ${uiState.loginModel} ")
                                val intent =
                                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                                finish()
                            }
                            is SplashScreenUIState.Error -> {
                                // No hay nada en dataStore
                                Log.i("user", "user: ${uiState.msg} ")
                                val intent =
                                    Intent(this@SplashScreenActivity, LoginActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                                finish()
                            }
                            else -> {}
                        }
                    }
                }
            }

        }, 2000)

    }
}