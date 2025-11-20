package com.alamin.smarthabitcompanion.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.room.relation.HabitWithRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Transaction
    @Query("SELECT * FROM HabitEntity")
    fun getAllHabits(): Flow<List<HabitWithRecord>>

    @Query("SELECT * FROM HabitEntity")
    fun getAllHabitList(): Flow<List<HabitEntity>>

    @Transaction
    @Query("SELECT * FROM HabitEntity WHERE id = :habitId")
    fun getHabitById(habitId: Int): Flow<HabitWithRecord?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

}