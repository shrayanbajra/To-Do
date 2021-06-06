package com.example.todo.ui.my_tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyTasksViewModel
@Inject
constructor(
    taskDao: TaskDao
) : ViewModel() {

    private val repository = MyTasksRepository(taskDao)

    fun getTasks(): LiveData<List<TaskEntity>> {
        return repository.getTasks()
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            repository.updateTask(task)
        }
    }

}