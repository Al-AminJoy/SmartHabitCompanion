package com.alamin.smarthabitcompanion.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alamin.smarthabitcompanion.data.di.AlarmEntryPoint
import dagger.hilt.android.EntryPointAccessors

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED",true)){
            val entryPoint = EntryPointAccessors.fromApplication(
                context.applicationContext,
                AlarmEntryPoint::class.java
            )
            entryPoint.scheduleAlarmUseCase().invoke()
        }
    }

}