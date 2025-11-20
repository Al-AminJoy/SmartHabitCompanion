package com.alamin.smarthabitcompanion.data.repository

import com.alamin.smarthabitcompanion.core.utils.extension.checkIsBefore
import com.alamin.smarthabitcompanion.data.local.room.dao.HabitDao
import com.alamin.smarthabitcompanion.data.local.room.dao.HabitRecordDao
import com.alamin.smarthabitcompanion.data.local.room.relation.HabitWithRecord
import com.alamin.smarthabitcompanion.data.mapper.toDomain
import com.alamin.smarthabitcompanion.data.mapper.toEntity
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import com.alamin.smarthabitcompanion.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitRecordDao: HabitRecordDao
) : HabitRepository {
    override suspend fun getAllHabit(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { it.map { it.toDomain() } }
    }

    override suspend fun getTodayHabitRecord(): Flow<List<Habit>> {
        val habits = habitDao.getAllHabits()
        val currentDate = LocalDate.now().toString()
        val todayHabit = habits.map {
            it.map { habit ->
                    val todayRecord = habit.records.filter { it.date.equals(currentDate, true) }
                    habit.copy(records = todayRecord).toDomain()
            }
        }

        return todayHabit

    }

    override suspend fun getSevenDayHabitRecord(): Flow<List<Habit>> {
        val habits = habitDao.getAllHabits()
        val currentDate = LocalDate.now().toString()
        val todayHabit = habits.map {
            it.map { habit ->
                val todayRecord = habit.records.filter { it.date.checkIsBefore(currentDate) }.sortedByDescending { it.date }.take(7)
                habit.copy(records = todayRecord).toDomain()
            }
        }
        return todayHabit

    }

    override suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun getHabitById(id: Int): Flow<Habit?> {
        return habitDao.getHabitById(id).map { it?.toDomain() }
    }

    override suspend fun getHabitByDateAndId(id: Int, date: String): Flow<Habit?> {
        val habit = habitDao.getHabitById(id)

        val todayHabit = habit.map { it ->
            if (it != null){
                val todayRecord = it.records.filter { it.date.equals(date, true) }
                it.copy(records = todayRecord).toDomain()
            }else{
                it
            }
        }

        return todayHabit

    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun getHabitRecordsByDate(date: String): Flow<List<HabitRecord>> {
        return habitRecordDao.getRecordsByDate(date).map { it.map { it.toDomain() } }
    }

    override suspend fun getRecordByHabitIdAndDate(
        habitId: Int,
        date: String
    ): Flow<List<HabitRecord>> {
        return habitRecordDao.getRecordByHabitIdAndDate(habitId, date).map {
            it.map { it.toDomain() }
        }
    }

    override suspend fun addRecord(record: HabitRecord) {
        habitRecordDao.insertRecord(record.toEntity())
    }

    override suspend fun updateRecord(record: HabitRecord) {
        habitRecordDao.updateRecord(record.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
    }
}