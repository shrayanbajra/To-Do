package com.example.todo.ui.addtask

import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

class AddTaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insert(task)
    }

}