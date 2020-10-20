package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    fun getTasks(): LiveData<List<TaskEntity>> {
        val repository = HomeRepository.getInstance()
        return repository.getAllTasks()
    }

    fun deleteAllTasks() {
        viewModelScope.launch(IO) {
            val repository = HomeRepository.getInstance()
            repository.deleteAllTasks()
        }
    }
}