package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository.getInstance()
    private val tasks = MutableLiveData<List<TaskEntity>>()

    fun getTasks(): LiveData<List<TaskEntity>> {
        return tasks
    }

    fun fetchTasks() {
        viewModelScope.launch(IO) {
            tasks.postValue(repository.getAllTasks())
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(IO) {
            repository.deleteAllTasks()
        }
    }
}