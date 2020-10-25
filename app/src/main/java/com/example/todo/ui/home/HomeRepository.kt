package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus

class HomeRepository(private val taskDao: TaskDao) {

    fun getRemainingTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getTasks(status = TaskStatus.NOT_DONE.value)
    }

    fun getCompletedTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getTasks(status = TaskStatus.DONE.value)
    }

    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.update(taskEntity)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }

}