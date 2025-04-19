package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.roomDb.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase(getApplication()).taskDao()
    val taskLoading = MutableLiveData<Boolean>(true)

    val taskListLiveData : LiveData<List<Task>> = taskDao.getAllTask()
        .onStart {
            taskLoading.postValue(true) //veri gelmeye başlıyor. Yükleniyor
        }
        .onEach {
            //delay(300)
            taskLoading.postValue(false) //veri geldi. Yüklendi.
        }
        .asLiveData(viewModelScope.coroutineContext+Dispatchers.IO)

    val totalTasks : LiveData<Int> = taskDao.getTaskCount()
    val completedTasks : LiveData<Int> = taskDao.getCompletedCount()

    fun setCompleteTask(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val isComplete = taskDao.getIsComplete(id)
            taskDao.setCompleteTask(id,!isComplete)
            //taskListLiveData.postValue(taskDao.getAllTask()) flow + asLiveData için gerek yok buna
        }
    }

    //MutableLiveData
    /*fun getDataFromRoom()
    {
        taskLoading.value=true

        viewModelScope.launch(Dispatchers.IO) {
            delay(700)
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val taskList = taskDao.getAllTask()
            withContext(Dispatchers.Main){
                showTask(taskList)
            }
        }
        println("HomeViewModel- getDataFromRoom çalıştı.")
    }*/

    /*private fun showTask(taskList: Flow<List<Task>>)
    {
        taskListLiveData.value = taskList
        taskLoading.value = false
    }*/

}