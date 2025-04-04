package com.example.dailytasktracker.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailytasktracker.model.Task

@Dao
interface TaskDAO {

    @Insert
    suspend fun insertAll(vararg task:Task) : List<Long> //eklenilen nesnelerinin id'sini long olarak döner.

    @Insert
    suspend fun insertTask(task:Task)

    @Update
    suspend fun updateTask(task:Task)

    @Query("delete from Task where taskId = :taskId")
    suspend fun deleteTask(taskId:Int)

    @Query("select * from Task")
    suspend fun getAllTask():List<Task>
    //Task @entity annotation' ı aldığı için tablo oldu artık.

    @Query("select * from Task where isFavourite = 1")
    suspend fun getAllFavTask():List<Task>

    @Query("delete from Task")
    suspend fun deleteAll()

    @Query("select * from Task where taskId = :taskId")
    suspend fun getTaskById(taskId:Int) : Task

    @Query("update Task set isFavourite = :taskFav where taskId = :taskId")
    suspend fun setFavouriteTask(taskId:Int,taskFav:Boolean)

    @Query("update Task set isComplete = :taskComplete where taskId = :taskId")
    suspend fun setCompleteTask(taskId:Int,taskComplete:Boolean)

}