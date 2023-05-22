package com.darksoft.sceii.data.network

import com.darksoft.sceii.data.network.login.LoginResponse
import com.darksoft.sceii.domain.login.dto.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/*
* Métodos para consumir la API
* */
interface SceiiApi {

    @POST("miembro/login")
    suspend fun postLogin(@Body loginDto: LoginDto): Response<LoginResponse>

}