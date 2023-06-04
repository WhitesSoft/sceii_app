package com.darksoft.sceii.core

import com.darksoft.sceii.data.network.SceiiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // Alcance de las dependencias, las instancias viviran mientras viva la aplicacion
object NetworkModule {

    @Provides
    @Singleton // Mostrar mensajes de log que contienen las peticiones y respuestas de la API
    fun provideOkInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // tiempo de espera de conexión 30 segundos
            .readTimeout(30, TimeUnit.SECONDS) // tiempo de espera de lectura 30 segundos
            .writeTimeout(30, TimeUnit.SECONDS) // tiempo de espera de escritura 30 segundos
            .build()
    }

    @Provides
    @Singleton // Unica instancia
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://sceii-backend.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Uso de la API
    @Provides
    fun provideSceiiApi(retrofit: Retrofit): SceiiApi {
        return retrofit.create(SceiiApi::class.java)
    }

}