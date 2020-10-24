package com.example.todo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(
    var someString: String
) : ViewModel() {

    private val repository = HomeRepository.getInstance()

    fun getTasks(): LiveData<List<TaskEntity>> {
        Log.d("HomeViewModel:", someString)
        return repository.getAllTasks()
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            repository.updateTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(IO) {
            repository.deleteAllTasks()
        }
    }
}