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

    val taskListLiveData = MutableLiveData<List<Task>>()
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
        println("HomeViewModel- getDataFromRoom çalıştı.")
    }

    private fun showTask(taskList:List<Task>)
    {
        taskListLiveData.value = taskList
        taskLoading.value = false
        taskErrorMessage.value = false
    }

    fun setCompleteTask(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val task = taskDao.getTaskById(id)
            taskDao.setCompleteTask(id,!task.isComplete)
            taskListLiveData.postValue(taskDao.getAllTask())
        }
    }

}