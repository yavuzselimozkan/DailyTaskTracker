package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.roomDb.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavouriteTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase(getApplication()).taskDao()

    val favTaskLiveData : LiveData<List<Task>> = taskDao.getAllFavTask()
        .onStart {
        }
        .onEach {
        }
        .asLiveData(viewModelScope.coroutineContext+Dispatchers.IO)


    fun setCompleteTask(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val isComplete = taskDao.getIsComplete(id)
            taskDao.setCompleteTask(id,!isComplete)
            //burada bunu güncellememizin sebebi adapter içerisindeki listedeki
            // elemanların isComplete değerine göre
            // checked ya unchecked durumu ele alınıyor bu yüzden liste güncellenmeli
            //favTaskLiveData.postValue(taskDao.getAllFavTask()) flow+liveData var gerek yok artık
        }
    }

    /*fun getAllFavTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            val favTaskList = taskDao.getAllFavTask()
            withContext(Dispatchers.Main){
                favTaskLiveData.value = favTaskList
            }
        }
    }*/

}