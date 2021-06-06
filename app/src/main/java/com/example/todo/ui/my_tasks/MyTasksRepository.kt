package com.example.todo.ui.my_tasks

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

class MyTasksRepository(private val taskDao: TaskDao) {

    fun getTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.update(taskEntity)
    }

}