package com.darksoft.sceii.data.network.login

import com.darksoft.sceii.domain.login.model.LoginModel
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("miembro") val miembro: Miembro,
    @SerializedName("token") val token: String
)

data class Miembro(
    @SerializedName("ru") val ru: Int,
    @SerializedName("ci") val ci: Int,
    @SerializedName("email") val email: String,
    @SerializedName("clave") val clave: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellidop") val apellidoPaterno: String,
    @SerializedName("apellidom") val apellidoMaterno: String,
    @SerializedName("celular") val celular: Int,
    @SerializedName("fechnac") val fechaNacimiento: String,
    @SerializedName("fechinsc") val fechaInscripcion: String,
    @SerializedName("activo") val activo: Boolean,
    @SerializedName("foto") val foto: String,
    @SerializedName("id_rol") val idRol: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

// Mapeamos los datos para la capa domain (tiene que tener el mismo nombre de la clase)
fun LoginResponse.toDomain() = LoginModel(
    token = this.token,
    ru = this.miembro.ru,
    ci = this.miembro.ci,
    email = this.miembro.email,
    nombre = this.miembro.nombre,
    apellidop = this.miembro.apellidoPaterno,
    apellidom = this.miembro.apellidoMaterno,
    celular = this.miembro.celular,
    fechnac = this.miembro.fechaNacimiento,
    fechinsc = this.miembro.fechaInscripcion,
    activo = this.miembro.activo,
    foto = this.miembro.foto,
    id_rol = this.miembro.idRol
)