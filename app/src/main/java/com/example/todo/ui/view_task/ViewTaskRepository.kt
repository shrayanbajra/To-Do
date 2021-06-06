package com.example.todo.ui.view_task

import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

class ViewTaskRepository
constructor(
    private val taskDao: TaskDao
) {

    suspend fun getTask(taskId: Int): TaskEntity? {
        return taskDao.get(taskId)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.update(task)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.delete(taskId)
    }
}