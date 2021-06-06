package com.example.todo.ui.view_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import com.example.todo.utils.SingleEventLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewTaskViewModel
@Inject
constructor(
    taskDao: TaskDao
) : ViewModel() {

    private val repository = ViewTaskRepository(taskDao)

    fun getTask(taskId: Int): LiveData<TaskEntity?> {
        val task = SingleEventLiveData<TaskEntity>()
        viewModelScope.launch(IO) {
            task.postValue(repository.getTask(taskId))
        }
        return task
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