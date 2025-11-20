package com.alamin.smarthabitcompanion.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitRecordDao {
    @Query("SELECT * FROM HabitRecordEntity WHERE date = :date")
    fun getRecordsByDate(date: String): Flow<List<HabitRecordEntity>>

    @Query("SELECT * FROM HabitRecordEntity WHERE habitId = :habitId AND date = :date")
    fun getRecordByHabitIdAndDate(habitId: Int, date: String): Flow<List<HabitRecordEntity>>

    @Query("SELECT * FROM HabitRecordEntity WHERE habitId = :habitId ORDER BY date DESC LIMIT 7")
    fun getLastSevenDayRecordByHabitId(habitId: Int): Flow<List<HabitRecordEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HabitRecordEntity)

    @Update
    suspend fun updateRecord(record: HabitRecordEntity)

    @Query("DELETE FROM HabitRecordEntity WHERE habitId = :habitId")
    suspend fun deleteRecordsForHabit(habitId: Int)
}