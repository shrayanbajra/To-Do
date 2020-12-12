package com.example.todo.utils.helpers

import com.example.todo.db.task.TaskEntity
import com.example.todo.utils.SortingOrder

object TasksHelper {

    fun getAlphabeticallySortedTasks(
        tasks: ArrayList<TaskEntity>,
        sortingOrder: SortingOrder
    ): ArrayList<TaskEntity> {

        val isAscending = sortingOrder == SortingOrder.VALUE_ASCENDING
        if (isAscending)
            tasks.sortBy { it.title }
        else
            tasks.sortByDescending { it.title }

        return tasks
    }

}