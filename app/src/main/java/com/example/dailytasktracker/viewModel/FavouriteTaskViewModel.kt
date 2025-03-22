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

class FavouriteTaskViewModel(application: Application) : AndroidViewModel(application) {

    val favTaskLiveData = MutableLiveData<List<Task>>()

    fun getAllFavTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val favTaskList = taskDao.getAllFavTask()
            withContext(Dispatchers.Main){
                favTaskLiveData.value = favTaskList
            }
        }
    }

}