package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import com.example.todo.utils.SingleEventLiveData
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
        val remainingTasks = SingleEventLiveData<List<TaskEntity>>()
        viewModelScope.launch(IO) {
            remainingTasks.postValue(repository.getRemainingTasks())
        }
        return remainingTasks
    }

    fun getCompletedTasks(): LiveData<List<TaskEntity>> {
        val completedTasks = SingleEventLiveData<List<TaskEntity>>()
        viewModelScope.launch(IO) {
            completedTasks.postValue(repository.getCompletedTasks())
        }
        return completedTasks
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