package com.alamin.smarthabitcompanion.data.local.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitRecordEntity
import com.alamin.smarthabitcompanion.data.local.room.entity.HabitEntity

data class HabitWithRecord(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    var records: List<HabitRecordEntity>
)
