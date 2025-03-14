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

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val taskLiveData = MutableLiveData<List<Task>>()
    val taskLoading = MutableLiveData<Boolean>()
    val taskErrorMessage = MutableLiveData<Boolean>()

    fun getDataFromRoom()
    {
        taskLoading.value=true

        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val taskList = taskDao.getAllTask()
            withContext(Dispatchers.Main){
                showTask(taskList)
            }
        }
    }

    private fun showTask(taskList:List<Task>)
    {
        taskLiveData.value = taskList
        taskLoading.value = false
        taskErrorMessage.value = false
    }

}