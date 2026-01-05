package com.alamin.smarthabitcompanion

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartHabitCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        createHabitReminderNotificationChannel()
    }

    private fun createHabitReminderNotificationChannel() {
        val channelId = "alarm_id"
        val channelName = "HabitReminder"
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

}