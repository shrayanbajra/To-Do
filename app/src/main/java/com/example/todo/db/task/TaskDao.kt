package com.example.todo.db.task

import androidx.room.Insert

interface TaskDao {

    @Insert
    suspend fun insert(task: TaskEntity)

}