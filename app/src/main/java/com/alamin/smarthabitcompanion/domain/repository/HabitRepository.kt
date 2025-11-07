package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun getAllHabit(): Flow<List<Habit>>

    suspend fun addHabit(habit: Habit)

    suspend fun addRecord(record: HabitRecord)
}