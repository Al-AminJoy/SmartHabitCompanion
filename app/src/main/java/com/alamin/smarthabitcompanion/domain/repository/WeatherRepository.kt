package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.data.remote.dto.WeatherResponseDto
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.model.Weather

interface WeatherRepository {
    suspend fun getCurrentWeather(param: CurrentWeatherRequestParam): Result<Weather>
}
