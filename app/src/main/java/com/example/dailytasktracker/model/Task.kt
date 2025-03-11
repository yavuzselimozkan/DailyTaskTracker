package com.example.dailytasktracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @ColumnInfo("taskName")
    var taskName:String,

    @ColumnInfo("taskDesc")
    var taskDesc:String,

    @ColumnInfo("isComplete")
    var isComplete:Boolean,

    @ColumnInfo("isFavourite")
    var isFavourite:Boolean,

    @ColumnInfo("taskDate")
    var taskDate:String,

){
    @PrimaryKey(autoGenerate = true)
    val taskId:Int = 0

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
