package com.example.dailytasktracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @ColumnInfo(name="taskName")
    var taskName:String,

    @ColumnInfo(name="taskDesc")
    var taskDesc:String,

    @ColumnInfo(name="isComplete")
    var isComplete:Boolean,

    @ColumnInfo(name="isFavourite")
    var isFavourite:Boolean,

    @ColumnInfo(name="taskDate")
    var taskDate:String,

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
