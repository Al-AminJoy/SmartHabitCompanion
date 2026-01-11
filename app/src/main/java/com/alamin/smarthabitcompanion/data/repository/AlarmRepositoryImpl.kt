package com.alamin.smarthabitcompanion.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import com.alamin.smarthabitcompanion.data.broadcast.AlarmReceiver
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.domain.model.AlarmItem
import com.alamin.smarthabitcompanion.domain.repository.AlarmRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context): AlarmRepository {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    @SuppressLint("ScheduleExactAlarm")
    override fun scheduleAlarm(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AppConstants.EXTRA_MESSAGE, alarmItem.message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmItem.alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmItem.hour)
            set(Calendar.MINUTE, alarmItem.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val triggerAtMillis = calendar.timeInMillis

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )

    }

    override fun cancelAlarm(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.alarmId.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    override fun hasPermission(): Boolean {
       return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
           alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}