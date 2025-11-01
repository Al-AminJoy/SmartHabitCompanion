package com.alamin.smarthabitcompanion.data.di

import com.alamin.smarthabitcompanion.core.network.APIService
import com.alamin.smarthabitcompanion.core.network.ServerConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(ServerConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().build())
            .build()

    }

    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): APIService{
        return retrofit.create(APIService::class.java)
    }

}