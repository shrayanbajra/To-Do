package com.example.todo.db.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(

    @ColumnInfo(name = "Status")
    var status: Int = TaskStatus.NOT_DONE.value,

    @ColumnInfo(name = "Task Title")
    var title: String = "",

    @ColumnInfo(name = "Task Description")
    var description: String = "",

    @ColumnInfo(name = "Date Added")
    var dateAdded: Long = 0L

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Task ID")
    var id: Int = 0

    fun isTaskDone(): Boolean {
        return status == TaskStatus.DONE.value
    }

    fun setTaskStatus(isChecked: Boolean) {
        status = if (isChecked)
            TaskStatus.DONE.value
        else
            TaskStatus.NOT_DONE.value
    }

}