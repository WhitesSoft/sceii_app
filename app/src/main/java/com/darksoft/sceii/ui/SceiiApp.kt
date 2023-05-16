package com.darksoft.sceii.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
* Clase de toda la aplicacion
* Configuramos Dagger Hilt y habilitamos la inyeccion de dependencias en toda la aplicacion
* */
@HiltAndroidApp
class SceiiApp: Application() {
}