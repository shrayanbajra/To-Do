package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class HomeRepository
@Inject
constructor(
    val taskDao: TaskDao
) {

    fun getAllTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.update(taskEntity)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }
}