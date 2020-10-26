package com.example.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.db.task.TaskDao
import com.example.todo.db.task.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}