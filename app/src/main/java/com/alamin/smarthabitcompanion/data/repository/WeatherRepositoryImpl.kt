package com.alamin.smarthabitcompanion.data.repository

import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.data.remote.dto.WeatherResponseDto
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.repository.WeatherRepository

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(param: CurrentWeatherRequestParam): Result<WeatherResponseDto> {
        TODO("Not yet implemented")
    }
}