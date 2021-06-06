package com.example.todo.ui.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel
@Inject
constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            val repository = AddTaskRepository(taskDao)
            repository.insertTask(task)
        }
    }

}