package com.example.todo.db.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table WHERE `Task ID` = :id")
    suspend fun get(id: Int): TaskEntity?

    @Query("SELECT * FROM task_table")
    suspend fun getAll(): List<TaskEntity>

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}