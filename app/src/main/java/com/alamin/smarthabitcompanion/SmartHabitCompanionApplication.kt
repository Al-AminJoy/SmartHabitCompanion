package com.alamin.smarthabitcompanion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartHabitCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}