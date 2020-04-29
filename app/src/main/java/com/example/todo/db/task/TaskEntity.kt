package com.example.todo.db.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @ColumnInfo(name = "Task Title")
    var taskTitle: String = "",

    @ColumnInfo(name = "Task Content")
    var taskContent: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Task ID")
    var id: Int = 0
}