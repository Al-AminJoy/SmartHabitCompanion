package com.alamin.smarthabitcompanion.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val isMale: Boolean,
)
