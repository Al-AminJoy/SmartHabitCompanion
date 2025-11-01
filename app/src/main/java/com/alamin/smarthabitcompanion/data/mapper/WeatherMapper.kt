package com.alamin.smarthabitcompanion.data.mapper

import com.alamin.smarthabitcompanion.data.local.entity.WeatherEntity
import com.alamin.smarthabitcompanion.data.remote.dto.WeatherResponseDto
import com.alamin.smarthabitcompanion.domain.model.Weather

fun WeatherResponseDto.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        0,
        this.location.name,
        this.location.country,
        this.currentDto.tempC,
        this.currentDto.lastUpdated,
        this.currentDto.conditionDto?.text,
        this.currentDto.conditionDto?.icon
    )}

fun WeatherResponseDto.toWeather(): Weather {
    return Weather(
        0,
        this.location.name,
        this.location.country,
        this.currentDto.tempC,
        this.currentDto.lastUpdated,
        this.currentDto.conditionDto?.text,
        this.currentDto.conditionDto?.icon
    )}

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        this.id,
        this.city,
        this.country,
        this.temperature,
        this.lastUpdated,
        this.condition,
        this.icon
    )}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        this.id,
        this.city,
        this.country,
        this.temperature,
        this.lastUpdated,
        this.condition,
        this.icon
    )}
