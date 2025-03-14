package com.example.dailytasktracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @ColumnInfo(name="taskName")
    val taskName:String,

    @ColumnInfo(name="taskDesc")
    val taskDesc:String,

    @ColumnInfo(name="isComplete")
    val isComplete:Boolean = false,

    @ColumnInfo(name="isFavourite")
    val isFavourite:Boolean = false,

    @ColumnInfo(name="taskDate")
    val taskDate:Long = System.currentTimeMillis()

){
    @PrimaryKey(autoGenerate = true)
    var taskId:Int = 0

    //GETTER - SETTER Yapılabilir.
    /*fun getTaskName():String
    {
        return this.taskName
    }
    fun setTaskName(taskName:String)
    {
        this.taskName = taskName
    }

    fun getTaskDesc():String
    {
        return this.taskDesc
    }
    fun setTaskDesc(taskDesc:String)
    {
        this.taskDesc = taskDesc
    }*/



}
