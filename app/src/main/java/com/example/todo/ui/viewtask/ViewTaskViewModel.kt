package com.example.todo.ui.viewtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ViewTaskViewModel : ViewModel() {

    private val repository = ViewTaskRepository.getInstance()

    fun getTask(id: Int): LiveData<TaskEntity?> {
        return repository.getTask(id)
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(IO) {
            repository.deleteTask(taskId)
        }
    }
}