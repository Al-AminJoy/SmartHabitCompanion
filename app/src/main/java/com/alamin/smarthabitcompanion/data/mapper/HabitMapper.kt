package com.alamin.smarthabitcompanion.data.mapper

import com.alamin.smarthabitcompanion.data.local.room.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitRecordEntity
import com.alamin.smarthabitcompanion.data.local.room.relation.HabitWithRecord
import com.alamin.smarthabitcompanion.domain.model.Habit
import com.alamin.smarthabitcompanion.domain.model.HabitRecord
import java.time.LocalDate

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        name = this.name,
        target = this.target,
        targetUnit = this.targetUnit,
        streakCount = this.streakCount,
        isCompleted = this.isCompleted
    )
}

fun HabitRecord.toEntity(): HabitRecordEntity{
    return HabitRecordEntity(
        habitId = this.habitId,
        date = this.date.toString(),
        progress = this.progress,
    )
}
fun HabitRecordEntity.toDomain(): HabitRecord {
    return HabitRecord(
        id = this.id,
        habitId = this.habitId,
        date = LocalDate.parse(this.date),
        progress = this.progress,
    )
}

fun HabitWithRecord.toDomain(): Habit{
   return  Habit(
        id = this.habit.id,
        name = this.habit.name,
        target = this.habit.target,
        targetUnit = this.habit.targetUnit,
        streakCount = this.habit.streakCount,
        isCompleted = this.habit.isCompleted,
        habitRecords = this.records.map { it.toDomain() }
    )
}