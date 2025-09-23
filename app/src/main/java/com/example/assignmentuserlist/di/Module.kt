package com.example.assignmentuserlist.di

import com.example.assignmentuserlist.BuildConfig
import com.example.assignmentuserlist.data.network.NetworkConfig.TIMEOUT
import com.example.assignmentuserlist.data.network.createAppApiClient
import com.example.assignmentuserlist.data.source.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
        val baseUrl = BuildConfig.BASE_API_URL
        val wMSRoleApiClient = createAppApiClient(baseUrl, okHttpClient)
        return wMSRoleApiClient.create(UserApi::class.java)
    }


}