package com.darksoft.sceii.data.repository

import com.darksoft.sceii.core.network.ErrorType
import com.darksoft.sceii.core.network.ResultType
import com.darksoft.sceii.data.network.SceiiApi
import com.darksoft.sceii.data.network.login.LoginResponse
import com.darksoft.sceii.data.network.login.toDomain
import com.darksoft.sceii.domain.login.dto.LoginDto
import com.darksoft.sceii.domain.login.model.LoginModel
import javax.inject.Inject

class SceiiRepository @Inject constructor(private val sceiiApi: SceiiApi) {

    suspend fun postLogin(loginDto: LoginDto): ResultType<LoginModel> {
        return try {
            val response = sceiiApi.postLogin(loginDto)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    ResultType.Success(loginResponse.toDomain())
                } ?:  ResultType.Error(ErrorType.UncontrolledError(-1))
            } else {
                val error = when (response.code()) {
                    ErrorType.BadRequest.errorCode -> ErrorType.BadRequest
                    ErrorType.Unauthorized.errorCode -> ErrorType.Unauthorized
                    ErrorType.Forbidden.errorCode -> ErrorType.Forbidden
                    ErrorType.NotFound.errorCode -> ErrorType.NotFound
                    ErrorType.InternalServerError.errorCode -> ErrorType.InternalServerError
                    else -> ErrorType.UncontrolledError(response.code())
                }
                ResultType.Error(error)
            }
        } catch (e: Exception) {
            ResultType.Error(ErrorType.ExceptionError(e))
        }
    }

}