package com.alamin.smarthabitcompanion.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.alamin.smarthabitcompanion.data.local.entity.HabitEntity
import com.alamin.smarthabitcompanion.data.local.entity.HabitRecordEntity

data class HabitWithRecord(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val records: List<HabitRecordEntity>
)
