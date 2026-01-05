package com.alamin.smarthabitcompanion.data.di

import com.alamin.smarthabitcompanion.data.repository.AlarmRepositoryImpl
import com.alamin.smarthabitcompanion.data.repository.HabitRepositoryImpl
import com.alamin.smarthabitcompanion.data.repository.ProfileRepositoryImpl
import com.alamin.smarthabitcompanion.data.repository.WeatherRepositoryImpl
import com.alamin.smarthabitcompanion.domain.repository.AlarmRepository
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import com.alamin.smarthabitcompanion.domain.repository.ProfileRepository
import com.alamin.smarthabitcompanion.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindAlarmRepository(impl: AlarmRepositoryImpl): AlarmRepository


}