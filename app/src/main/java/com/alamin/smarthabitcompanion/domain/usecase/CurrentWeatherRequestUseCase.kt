package com.alamin.smarthabitcompanion.domain.usecase

import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentWeatherRequestUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(param: CurrentWeatherRequestParam): Result<Weather> {
     return repository.requestCurrentWeather(param)
    }
}