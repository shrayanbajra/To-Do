package com.example.todo.db.task

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM task_table WHERE `Task ID` = :id")
    fun get(id: Int): LiveData<TaskEntity?>

    @Query("SELECT * FROM task_table")
    fun getAll(): LiveData<List<TaskEntity>>

    @Query("DELETE FROM task_table WHERE `Task ID` = :taskID")
    suspend fun delete(taskID: Int)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}