package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository.getInstance()

    fun getTasks(): LiveData<List<TaskEntity>> {
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