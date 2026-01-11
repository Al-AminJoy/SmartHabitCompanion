package com.alamin.smarthabitcompanion.domain.model

data class AlarmItem(
    val alarmId: Long,
    val hour: Int,
    val minute: Int,
    val message: String
)
