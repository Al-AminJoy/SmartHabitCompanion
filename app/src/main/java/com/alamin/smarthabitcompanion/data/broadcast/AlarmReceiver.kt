package com.alamin.smarthabitcompanion.data.broadcast

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.alamin.smarthabitcompanion.R
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.data.di.AlarmEntryPoint
import com.alamin.smarthabitcompanion.domain.usecase.AlarmSchedulerUseCase
import com.alamin.smarthabitcompanion.ui.presentation.main.MainActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class AlarmReceiver: BroadcastReceiver() {

    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra(AppConstants.EXTRA_MESSAGE) ?: return

        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            AlarmEntryPoint::class.java
        )
        entryPoint.scheduleAlarmUseCase().invoke()

        showNotification(context, message)

    }

    private fun showNotification(context: Context, message: String){
        val intent = Intent(context, MainActivity::class.java).apply {
            setFlags( Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Habit Reminder !")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        notificationManager.notify(1, builder.build())
    }
}