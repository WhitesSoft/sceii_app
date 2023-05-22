package com.darksoft.sceii.domain.login

import com.darksoft.sceii.core.network.ResultType
import com.darksoft.sceii.data.repository.SceiiRepository
import com.darksoft.sceii.domain.login.dto.LoginDto
import com.darksoft.sceii.domain.login.model.LoginModel
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(private val sceiiRepository: SceiiRepository) {

    // Con coroutines
    suspend operator fun invoke(loginDto: LoginDto): ResultType<LoginModel> =
        sceiiRepository.postLogin(loginDto)

}