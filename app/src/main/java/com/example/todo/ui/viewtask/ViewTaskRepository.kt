package com.example.todo.ui.viewtask

import androidx.lifecycle.LiveData
import com.example.todo.db.task.TaskEntity
import com.example.todo.ui.BaseRepository

class ViewTaskRepository : BaseRepository() {

    companion object {
        private var instance: ViewTaskRepository? = null

        fun getInstance() = instance ?: ViewTaskRepository()
    }

    private val taskDao = getDatabase().taskDao()

    fun getTask(id: Int): LiveData<TaskEntity?> {
        return taskDao.get(id)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.update(task)
    }

    suspend fun deleteTask(taskID: Int) {
        taskDao.delete(taskID)
    }
}