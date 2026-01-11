package com.alamin.smarthabitcompanion.data.di

import com.alamin.smarthabitcompanion.domain.usecase.AlarmSchedulerUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmEntryPoint {
    fun scheduleAlarmUseCase(): AlarmSchedulerUseCase
}