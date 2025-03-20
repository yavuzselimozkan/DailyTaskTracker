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

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    var taskLiveData = MutableLiveData<Task>()
    var taskFavLiveData = MutableLiveData<Boolean>(false)

    fun getTaskDetail(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val task = taskDao.getTaskById(id)
            //live dataya value verirken IO threadte yaparsan hata alırsın.
            withContext(Dispatchers.Main){
                taskLiveData.value = task
            }
        }
    }

    fun deleteTask(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.deleteTask(id)
        }
    }

    fun setFavouriteTask(id:Int){
        //taskLiveData nın tersini alıyoruz. True ise not yani false oluyor. Eğer false ise true. Db deki nesnenin değeri
        val newFavState = taskLiveData.value?.isFavourite?.not() ?: true
        //Sonra bunu veri tabanı işlemi için Dao ya veriyoruz.

        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.setFavouriteTask(id,newFavState)
            withContext(Dispatchers.Main){
                loadFavouriteState(newFavState)
            }
        }
    }

    private fun loadFavouriteState(favState:Boolean){
        taskFavLiveData.value = favState
    }
}