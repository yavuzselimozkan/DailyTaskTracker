package com.example.dailytasktracker.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dailytasktracker.roomDb.TaskDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyCleanupWorker(context:Context, workerParameters:WorkerParameters): CoroutineWorker(context,workerParameters){
    //Bu sınıf her günün sonunda favori olmayan görevleri silecek. Bunu da workManager ile yapacak.

    override suspend fun doWork(): Result {

        logCurrentTime("İşlem Başladı")
        val taskDao = TaskDatabase.invoke(applicationContext).taskDao()

        try{
            logCurrentTime("İşlem Yapılıyor")
            taskDao.deleteNonFavouriteTask()
        }catch (e:Exception){
            Log.d("DailyCleanupWorker","Hata: $e")
            return Result.failure()
        }

        logCurrentTime("İşlem Tamamlandı")
        return Result.success()
    }

    private fun logCurrentTime(stage: String) {
        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        Log.d("DailyCleanupWorker", "$stage Time: $formattedDate")
    }

}