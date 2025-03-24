package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.roomDb.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateTaskViewModel(application: Application) : AndroidViewModel(application) {
    val updatedLiveData = MutableLiveData<Task>()

    fun getTaskById(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val task = taskDao.getTaskById(id)
            withContext(Dispatchers.Main){
                updatedLiveData.value = task
            }
        }
    }

    fun updateTask(updatedTask:Task){
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.updateTask(updatedTask)
        }
    }
}