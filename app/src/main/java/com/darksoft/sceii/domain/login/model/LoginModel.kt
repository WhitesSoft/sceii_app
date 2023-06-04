package com.darksoft.sceii.domain.login.model

data class LoginModel(
    val token: String,
    val ru: Int,
    val ci: Int,
    val email: String,
    val nombre: String,
    val apellidop: String,
    val apellidom: String,
    val celular: Int,
    val fechnac: String,
    val fechinsc: String,
    val activo: Boolean,
    val foto: String,
    val id_rol: Int,
)