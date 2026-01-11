package com.alamin.smarthabitcompanion

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartHabitCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        createHabitReminderNotificationChannel()
    }

    private fun createHabitReminderNotificationChannel() {
        val channelName = "HabitReminder"
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            AppConstants.CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

}