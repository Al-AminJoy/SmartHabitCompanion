package com.alamin.smarthabitcompanion.domain.repository

import com.alamin.smarthabitcompanion.domain.model.AlarmItem


interface AlarmRepository {
    fun scheduleAlarm(alarmItem: AlarmItem)
    fun cancelAlarm(alarmItem: AlarmItem)
    fun hasPermission(): Boolean
}