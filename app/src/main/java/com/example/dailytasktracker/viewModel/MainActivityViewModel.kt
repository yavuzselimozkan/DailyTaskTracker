package com.example.dailytasktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dailytasktracker.roomDb.TaskDatabase

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase(getApplication()).taskDao()
    val favouriteCount: LiveData<Int> = taskDao.getFavouriteCount()
}