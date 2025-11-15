package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun getAllHabit(): Flow<List<Habit>>

    suspend fun addHabit(habit: Habit)

    suspend fun getHabitById(id: Int): Flow<Habit?>

    suspend fun updateHabit(habit: Habit)


    suspend fun getHabitRecordsByDate(date: String): Flow<List<HabitRecord>>
    suspend fun getRecordByHabitIdAndDate(habitId: Int, date: String): Flow<List<HabitRecord>>
    suspend fun addRecord(record: HabitRecord)

    suspend fun updateRecord(record: HabitRecord)

    suspend fun deleteHabit(habit: Habit)

}

