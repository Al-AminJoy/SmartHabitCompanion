package com.alamin.smarthabitcompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.data.local.dao.HabitDao
import com.alamin.smarthabitcompanion.data.local.dao.HabitRecordDao
import com.alamin.smarthabitcompanion.data.local.dao.WeatherDao
import com.alamin.smarthabitcompanion.data.local.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.entity.HabitRecordEntity
import com.alamin.smarthabitcompanion.data.local.entity.WeatherEntity

@Database(entities = [WeatherEntity::class, HabitEntity::class, HabitRecordEntity::class], version = AppConstants.DATABASE_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun habitDao(): HabitDao
    abstract fun habitRecordDao(): HabitRecordDao


}