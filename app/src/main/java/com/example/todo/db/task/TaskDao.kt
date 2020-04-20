package com.example.todo.db.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("SELECT * FROM task_table")
    suspend fun getAll(): List<TaskEntity>

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}