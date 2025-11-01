package com.alamin.smarthabitcompanion.core.network

import com.alamin.smarthabitcompanion.data.remote.dto.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface APIService {
    @GET(ApiEndPoints.WEATHER)
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String): Response<WeatherResponseDto>
}