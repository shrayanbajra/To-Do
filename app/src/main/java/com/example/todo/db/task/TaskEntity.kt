package com.example.todo.db.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "task_table")
data class TaskEntity(

    @ColumnInfo(name = "Status")
    var status: Int = TaskStatus.NOT_DONE.value,

    @ColumnInfo(name = "Task Title")
    var title: String = "",

    @ColumnInfo(name = "Task Description")
    var description: String = "",

    @ColumnInfo(name = "Date Added")
    val dateAdded: Long = System.currentTimeMillis()

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Task ID")
    val id: Int = 0

    val dateAddedInFormattedForm: String
        get() = DateFormat.getDateTimeInstance().format(dateAdded)

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