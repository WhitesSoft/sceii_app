package com.darksoft.sceii.domain.preferences

import com.darksoft.sceii.data.repository.PreferencesRepository
import javax.inject.Inject

class GetDataUserUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository)   {
    suspend fun invoke(key: String) = preferencesRepository.getDataUser(key)
}