package com.example.todo.ui.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.task.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AddTaskViewModel : ViewModel() {

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(IO) {
            val repository = AddTaskRepository.getInstance()
            repository.insertTask(task)
        }
    }
}