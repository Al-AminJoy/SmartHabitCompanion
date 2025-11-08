package com.alamin.smarthabitcompanion.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.data.local.room.dao.HabitDao
import com.alamin.smarthabitcompanion.data.local.room.dao.HabitRecordDao
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitRecordEntity
import com.alamin.smarthabitcompanion.data.local.room.dao.WeatherDao
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.room.entity.WeatherEntity

@Database(entities = [WeatherEntity::class, HabitEntity::class, HabitRecordEntity::class], version = AppConstants.DATABASE_VERSION,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun habitDao(): HabitDao
    abstract fun habitRecordDao(): HabitRecordDao


}