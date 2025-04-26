package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.roomDb.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NewTaskViewModel(application: Application) : AndroidViewModel(application) {

    fun insertTask(task:Task)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = TaskDatabase(getApplication()).taskDao()
            taskDao.insertTask(task)
        }
    }

    private fun calculateInitialDelay():Long{
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.set(Calendar.HOUR_OF_DAY,18)
        target.set(Calendar.MINUTE,30)
        target.set(Calendar.SECOND,0)

        if(target.before(now)){
            //eğer hedef saati geçildiyse
            target.add(Calendar.DAY_OF_YEAR,1)
        }
        return target.timeInMillis - now.timeInMillis
    }
}