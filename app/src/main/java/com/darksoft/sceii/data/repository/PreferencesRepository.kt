package com.darksoft.sceii.data.repository

import com.darksoft.sceii.data.preferences.Preferences
import com.darksoft.sceii.domain.login.model.LoginModel
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val preferences: Preferences) {
    suspend fun saveDataUser(key: String, loginModel: LoginModel) {
        preferences.saveDataUser(key, loginModel)
    }

    suspend fun getDataUser(key: String): LoginModel? {
        return preferences.getDataUser(key)
    }
}