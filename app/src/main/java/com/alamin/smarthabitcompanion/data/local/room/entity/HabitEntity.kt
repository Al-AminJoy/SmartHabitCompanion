package com.alamin.smarthabitcompanion.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val target: Int?,
    val targetUnit: String?,
    val streakCount: Int = 0,
    val isCompleted: Boolean = false
)