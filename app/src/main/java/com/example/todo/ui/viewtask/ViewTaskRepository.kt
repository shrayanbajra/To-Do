package com.example.todo.ui.viewtask

import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import javax.inject.Inject

class ViewTaskRepository
@Inject
constructor(
    val taskDao: TaskDao
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