package com.alamin.smarthabitcompanion.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alamin.smarthabitcompanion.data.local.room.entity.WeatherEntity
import com.alamin.smarthabitcompanion.domain.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherByCity(): Flow<List<Weather>>

    @Query("SELECT * FROM WeatherEntity LIMIT 1")
    fun getWeather(): Flow<WeatherEntity?>

    @Query("DELETE FROM WeatherEntity")
    fun deleteWeather()

    @Transaction
    suspend fun deleteAndInsertWeather(weather: WeatherEntity) {
        deleteWeather()
        insert(weather)
    }



}