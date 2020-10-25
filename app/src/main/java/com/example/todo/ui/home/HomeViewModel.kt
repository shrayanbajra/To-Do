package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(
    taskDao: TaskDao
) : ViewModel() {

    private val repository = HomeRepository(taskDao)

    fun getRemainingTasks(): LiveData<List<TaskEntity>> {
        return repository.getRemainingTasks()
    }

    fun getCompletedTasks(): LiveData<List<TaskEntity>> {
        return repository.getCompletedTasks()
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