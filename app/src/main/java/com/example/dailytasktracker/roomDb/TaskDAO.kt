package com.example.dailytasktracker.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailytasktracker.model.Task
import kotlinx.coroutines.flow.Flow

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
    fun getAllTask(): Flow<List<Task>>
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

    @Query("delete from Task where isFavourite = 0")
    suspend fun deleteNonFavouriteTask()

    @Query("select isComplete from Task where taskId = :taskId")
    suspend fun getIsComplete(taskId:Int) : Boolean

    @Query("select count(*) from Task")
    fun getTaskCount() : LiveData<Int>

    @Query("select count(*) from Task where isComplete = 1")
    fun getCompletedCount() : LiveData<Int>

    @Query("select count(*) from Task where isFavourite = 1")
    fun getFavouriteCount() : LiveData<Int>

}