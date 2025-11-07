package com.alamin.smarthabitcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val target: Int?,
    val targetUnit: Int?,
    val streakCount: Int = 0
)