package com.example.todo.ui.viewtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ViewTaskViewModel : ViewModel() {

    private val repository = ViewTaskRepository.getInstance()

    private val task = MutableLiveData<TaskEntity>()

    fun getTaskLiveData(): LiveData<TaskEntity> {
        return task
    }

    fun getTask(id: Int) {
        viewModelScope.launch(IO) {
            val taskEntity = repository.getTask(id) ?: TaskEntity()
            task.postValue(taskEntity)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(taskID: Int) {
        viewModelScope.launch(IO) {
            repository.deleteTask(taskID)
        }
    }
}