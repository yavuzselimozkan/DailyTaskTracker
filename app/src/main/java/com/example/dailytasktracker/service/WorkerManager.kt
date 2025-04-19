package com.example.dailytasktracker.service

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

//Bu fonksiyonu uygulama başladığında çağıracağız.
// Bu sayede uygulama açıldığında fonksiyomuz hazır olacak

fun setUpDailyCleanupWorker(context: Context) {

    val currentTime = Calendar.getInstance().timeInMillis
    val midNightTime = calculateMidNightTime()
    val initialDelay = midNightTime - currentTime

    //OneTimeWorkRequestBuilder ile işimizi oluşturuyoruz.
    val dailyCleanupRequest = OneTimeWorkRequestBuilder<DailyCleanupWorker>()
        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
        .build()

    //bu requesti işi kaydedeceğiz
    WorkManager.getInstance(context).enqueueUniqueWork(
        "DailyCleanupWorker",
        ExistingWorkPolicy.KEEP,
        dailyCleanupRequest
    )


}

fun calculateMidNightTime():Long{
    //gece yarısı ayarlanacak
    val midNightTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY,0)
        set(Calendar.MINUTE,0)
        set(Calendar.SECOND,0)
        set(Calendar.MILLISECOND,0)
        add(Calendar.DAY_OF_YEAR,1) //bir sonraki gece yarısı için ayarla
    }
    return midNightTime.timeInMillis
}

/*fun testWorker(context: Context){

    val testCleanupRequest = OneTimeWorkRequestBuilder<DailyCleanupWorker>()
        .setInitialDelay(3,TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "TestCleanup",
        ExistingWorkPolicy.REPLACE,
        testCleanupRequest
    )

}
fun testKeepWorker(context: Context){

    val testKeepCleanupRequest = OneTimeWorkRequestBuilder<DailyCleanupWorker>()
        .setInitialDelay(3,TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "TestKeepCleanup",
        ExistingWorkPolicy.KEEP,
        testKeepCleanupRequest
    )

}

fun cancelTestWorker(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("TestCleanup")
}

fun cancelAllWorkers(context: Context) {
    WorkManager.getInstance(context).cancelAllWork()  // Tüm işlerin iptal edilmesini sağlar.
    Log.d("WorkerCleanup", "Tüm işler iptal edildi.")
}

fun watchWorker(context:Context){
    // Schedule edilen worker'ların durumlarını gözlemlemek için LiveData kullanıyoruz.
    WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData("TestCleanup")
        .observeForever { workInfos ->
            if (workInfos != null && workInfos.isNotEmpty()) {
                // Gözlemlenen her bir worker durumu log'a yazılır.
                workInfos.forEach { workInfo ->
                    Log.d("MyApplication", "Worker durumu: ${workInfo.state}")
                }
            } else {
                Log.d("MyApplication", "Henüz schedule edilmiş worker bulunamadı.")
            }
        }
}

fun pruneWork(context: Context){
    WorkManager.getInstance(context).pruneWork()
}*/
