package com.example.todo.ui.mytasks

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

class MyTasksRepository(private val taskDao: TaskDao) {

    fun getAll(): LiveData<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.update(taskEntity)
    }

}