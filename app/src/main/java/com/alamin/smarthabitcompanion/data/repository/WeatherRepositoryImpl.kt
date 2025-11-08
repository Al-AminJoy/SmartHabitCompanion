package com.alamin.smarthabitcompanion.data.repository

import com.alamin.smarthabitcompanion.core.network.APIService
import com.alamin.smarthabitcompanion.core.utils.Result
import com.alamin.smarthabitcompanion.core.utils.extension.getException
import com.alamin.smarthabitcompanion.core.utils.extension.getSpecificException
import com.alamin.smarthabitcompanion.data.local.room.dao.WeatherDao
import com.alamin.smarthabitcompanion.data.mapper.toWeather
import com.alamin.smarthabitcompanion.data.mapper.toWeatherEntity
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.model.Weather
import com.alamin.smarthabitcompanion.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: APIService, private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun requestCurrentWeather(param: CurrentWeatherRequestParam): Result<Weather> {
        return try {
            val response = apiService.getCurrentWeather(param.city, param.key)

            if (response.isSuccessful && response.body() != null) {
                weatherDao.deleteAndInsertWeather(response.body()!!.toWeatherEntity())
                Result.Success(response.body()!!.toWeather())
            } else {
                Result.Error(response.getException())
            }

        } catch (e: Exception) {
            Result.Error(e.getSpecificException())
        }
    }

    override suspend fun getCurrentWeather(): Flow<Weather?> {
        return weatherDao.getWeather().map { it?.toWeather() }
    }
}