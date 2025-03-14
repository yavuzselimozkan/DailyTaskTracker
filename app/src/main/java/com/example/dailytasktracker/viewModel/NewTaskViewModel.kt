package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.roomDb.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewTaskViewModel(application: Application) : AndroidViewModel(application) {

    val taskLiveData = MutableLiveData<Task>()

    fun getTaskDetail(taskId:Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskLiveData.value = taskDao.getTaskById(taskId)
        }
    }

    fun insertTask(task:Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.insertTask(task)
        }
    }

    fun updateTask(newTask:Task)
    {
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.updateTask(newTask)
        }
    }

    fun deleteTask(deleteTask:Task)
    {
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.deleteTask(deleteTask)
        }
    }
}