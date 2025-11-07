package com.alamin.smarthabitcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alamin.smarthabitcompanion.data.local.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.relation.HabitWithRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Transaction
    @Query("SELECT * FROM HabitEntity")
    fun getAllHabits(): Flow<List<HabitWithRecord>>

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