package com.alamin.smarthabitcompanion.data.repository

import com.alamin.smarthabitcompanion.data.local.dao.HabitDao
import com.alamin.smarthabitcompanion.data.local.dao.HabitRecordDao
import com.alamin.smarthabitcompanion.data.mapper.toDomain
import com.alamin.smarthabitcompanion.data.mapper.toEntity
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val habitDao: HabitDao, private val habitRecordDao: HabitRecordDao) : HabitRepository {
    override suspend fun getAllHabit(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { it.map { it.toDomain() } }
    }

    override suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun getHabitById(id: Int): Flow<Habit?> {
        return habitDao.getHabitById(id).map { it?.toDomain() }
    }

    override suspend fun getHabitRecordsByDate(date: String): Flow<List<HabitRecord>> {
        return habitRecordDao.getRecordsByDate(date).map { it.map { it.toDomain() } }
    }

    override suspend fun getRecordByHabitIdAndDate(
        habitId: Int,
        date: String
    ): Flow<HabitRecord?> {
        return habitRecordDao.getRecordByHabitIdAndDate(habitId, date).map { it?.toDomain()
    }}

    override suspend fun addRecord(record: HabitRecord) {
        habitRecordDao.insertRecord(record.toEntity())
    }

    override suspend fun updateRecord(record: HabitRecord) {
       habitRecordDao.updateRecord(record.toEntity())
    }
}