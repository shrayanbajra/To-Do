package com.example.todo.ui.addtask

import com.example.todo.db.task.TaskEntity
import com.example.todo.ui.BaseRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AddTaskRepository private constructor() : BaseRepository() {

    companion object {
        private var instance: AddTaskRepository? = null

        fun getInstance() = instance ?: AddTaskRepository()
    }

    private val taskDao by lazy {
        getDatabase().taskDao()
    }

    suspend fun insertTask(task: TaskEntity) {
        withContext(IO) {
            taskDao.insert(task)
        }
    }

    suspend fun getAllTasks() {
        withContext(IO) {
            taskDao.getAll()
        }
    }
}