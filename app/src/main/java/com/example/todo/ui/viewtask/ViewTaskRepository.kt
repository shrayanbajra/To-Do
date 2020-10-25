package com.example.todo.ui.viewtask

import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

class ViewTaskRepository
constructor(
    private val taskDao: TaskDao
) {

    suspend fun getTask(id: Int): TaskEntity? {
        return taskDao.get(id)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.update(task)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.delete(taskId)
    }
}