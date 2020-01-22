package com.zip.shared.di

import com.google.gson.GsonBuilder
import com.zip.shared.BuildConfig
import com.zip.shared.api.NetworkService
import com.zip.shared.util.extensions.DateFormat
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val TIME_OUT = 45L
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        getBasicOkHttpClient().build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder().setDateFormat(DateFormat.yyyy_MM_dd.format).create()
        )

    @Provides
    @Singleton
    fun provideNetworkService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): NetworkService =
        createService(okHttpClient, converterFactory, NetworkService::class.java)

    private fun getBasicOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> createService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }

}