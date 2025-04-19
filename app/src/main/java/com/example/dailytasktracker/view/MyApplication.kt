package com.example.dailytasktracker.view

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.example.dailytasktracker.service.setUpDailyCleanupWorker

class MyApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        setUpDailyCleanupWorker(applicationContext)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
    }
}