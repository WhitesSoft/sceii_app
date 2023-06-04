package com.darksoft.sceii.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.darksoft.sceii.core.constants.PREFERENCES_NAME
import com.darksoft.sceii.domain.login.model.LoginModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class Preferences @Inject constructor(private val context: Context) {

    suspend fun saveDataUser(key: String, loginModel: LoginModel){
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = loginModel.toString()
        }
    }

    suspend fun getDataUser(key: String): LoginModel? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        val serializedValue = preferences[preferencesKey]

        return if (serializedValue != null) {
            val loginModel = deserializeLoginModel(serializedValue)
            loginModel
        } else {
            null
        }
    }

    private fun deserializeLoginModel(serializedValue: String): LoginModel {
        val values = serializedValue.split(",")
        return LoginModel(
            token = values[0],
            ru = values[1].toInt(),
            ci = values[2].toInt(),
            email = values[3],
            nombre = values[4],
            apellidop = values[5],
            apellidom = values[6],
            celular = values[7].toInt(),
            fechnac = values[8],
            fechinsc = values[9],
            activo = values[10].toBoolean(),
            foto = values[11],
            id_rol = values[12].toInt()
        )
    }

}