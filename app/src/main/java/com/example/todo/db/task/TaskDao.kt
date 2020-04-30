package com.example.todo.db.task

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM task_table WHERE `Task ID` = :id")
    suspend fun get(id: Int): TaskEntity?

    @Query("SELECT * FROM task_table")
    suspend fun getAll(): List<TaskEntity>

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}