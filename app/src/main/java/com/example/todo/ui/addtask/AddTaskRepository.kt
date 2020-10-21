package com.example.todo.ui.addtask

import com.example.todo.db.task.TaskEntity
import com.example.todo.ui.BaseRepository

class AddTaskRepository private constructor() : BaseRepository() {

    companion object {
        private var instance: AddTaskRepository? = null

        fun getInstance() = instance ?: AddTaskRepository()
    }

    private val taskDao by lazy {
        getDatabase().taskDao()
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insert(task)
    }
}