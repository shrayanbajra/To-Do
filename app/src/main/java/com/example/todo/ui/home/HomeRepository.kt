package com.example.todo.ui.home

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity
import com.example.todo.ui.BaseRepository

class HomeRepository private constructor() : BaseRepository() {

    companion object {
        private var instance: HomeRepository? = null

        fun getInstance() = instance ?: HomeRepository()
    }

    private val taskDao: TaskDao by lazy { getDatabase().taskDao() }

    fun getAllTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }
}