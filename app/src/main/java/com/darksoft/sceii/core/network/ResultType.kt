package com.darksoft.sceii.core.network

// La T recibira un valor de cualquier tipo
sealed class ResultType<T> {
    data class Success<T>(val data: T?) : ResultType<T>()
    data class Error<T>(val errorType: ErrorType) : ResultType<T>()
    // tambien puede entrar diferentes estados como offline
}