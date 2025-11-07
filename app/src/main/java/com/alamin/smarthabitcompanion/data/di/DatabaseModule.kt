package com.alamin.smarthabitcompanion.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.data.local.AppDatabase
import com.alamin.smarthabitcompanion.data.local.dao.HabitDao
import com.alamin.smarthabitcompanion.data.local.dao.HabitRecordDao
import com.alamin.smarthabitcompanion.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder<AppDatabase>(appContext, AppConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao = appDatabase.weatherDao()

    @Provides
    @Singleton
    fun provideHabitDao(appDatabase: AppDatabase): HabitDao = appDatabase.habitDao()

    @Provides
    @Singleton
    fun provideHabitRecordDao(appDatabase: AppDatabase): HabitRecordDao = appDatabase.habitRecordDao()


}