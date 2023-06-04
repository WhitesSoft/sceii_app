package com.darksoft.sceii.domain.preferences

import com.darksoft.sceii.data.repository.PreferencesRepository
import com.darksoft.sceii.domain.login.model.LoginModel
import javax.inject.Inject

class SaveDataUserUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(key: String, loginModel: LoginModel) {
        preferencesRepository.saveDataUser(key, loginModel)
    }
}