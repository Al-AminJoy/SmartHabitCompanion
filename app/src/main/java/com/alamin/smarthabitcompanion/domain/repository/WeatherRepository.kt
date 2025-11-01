package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun requestCurrentWeather(param: CurrentWeatherRequestParam): Result<Weather>
    suspend fun getCurrentWeather(): Flow<Weather?>

}
