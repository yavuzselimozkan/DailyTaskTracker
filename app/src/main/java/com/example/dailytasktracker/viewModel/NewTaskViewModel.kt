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

    fun insertTask(task:Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.insertTask(task)
        }
    }
}