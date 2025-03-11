package com.example.dailytasktracker.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailytasktracker.model.Task

@Database(entities = [Task::class],version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDAO

    //senkronize bir şekilde database nesnesi oluşturmaya yarar. Bu sayede data race i engeller.
    //burada singleton nesne oluşturuluyor. Eğer daha önce oluşturulduysa aynı nesne kullanılmaya devam edilecek.
    //ana öğeler instance,lock invoke

    companion object{

        @Volatile
        private var databaseInstance:TaskDatabase ?= null
        private var lock = Any()

        //bu fonksiyon nesne çağırıldığı zaman, oluşturulduğu zaman çağrılır. Eğer nesne varsa instance direkt geri döner.
        operator fun invoke(context:Context) = databaseInstance ?: synchronized(lock){
            //senkronize olması data race i engellemek için
            databaseInstance?:createDatabase(context).also{
                //eğer boşsa gelen nesneyi atayacak.
                databaseInstance = it
            }
        }

        private fun createDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "TaskDatabase"
        ).build()


    }



}