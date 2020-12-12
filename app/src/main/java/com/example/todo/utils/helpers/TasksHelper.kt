package com.example.todo.utils.helpers

import com.example.todo.db.task.TaskEntity
import com.example.todo.utils.SortingOrder

object TasksHelper {

    fun getAlphabeticallySortedTasks(tasks: ArrayList<TaskEntity>, sortingOrder: SortingOrder)

            : ArrayList<TaskEntity> {

        if (isAscending(sortingOrder))
            tasks.sortBy { it.title }
        else
            tasks.sortByDescending { it.title }

        return tasks
    }

    fun getTasksSortedByCompletionStatus(tasks: ArrayList<TaskEntity>, sortingOrder: SortingOrder)

            : MutableList<TaskEntity> {

        if (isAscending(sortingOrder))
            tasks.sortBy { it.isTaskDone() }
        else
            tasks.sortByDescending { it.isTaskDone() }

        return tasks
    }

    fun getTasksSortedByDateAdded(tasks: ArrayList<TaskEntity>, sortingOrder: SortingOrder)

            : MutableList<TaskEntity> {

        if (isAscending(sortingOrder))
            tasks.sortBy { it.dateAdded }
        else
            tasks.sortByDescending { it.dateAdded }

        return tasks

    }

    private fun isAscending(sortingOrder: SortingOrder): Boolean {
        return sortingOrder == SortingOrder.VALUE_ASCENDING
    }

}