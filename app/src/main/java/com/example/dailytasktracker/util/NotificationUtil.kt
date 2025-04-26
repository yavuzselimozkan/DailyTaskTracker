package com.example.dailytasktracker.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationUtil {
    fun scheduleNotification(context: Context){

        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1,TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(),TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_notification_Worker2",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationRequest)
    }

    fun cancelNotification(context:Context){
        WorkManager.getInstance(context).cancelUniqueWork("daily_notification_Worker2")
    }

    private fun calculateInitialDelay():Long{
        val now = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply{
            set(Calendar.HOUR_OF_DAY,15)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)
            //Eğer saat 15.00 i geçerse
            if(before(now)){
                add(Calendar.DAY_OF_YEAR,1)
            }
        }
        return targetTime.timeInMillis - now.timeInMillis
    }
}