package com.example.todo.ui.mytasks

import androidx.recyclerview.widget.DiffUtil
import com.example.todo.db.task.TaskEntity

class TasksDiffCallback(
    private val oldList: List<TaskEntity>,
    private val newList: List<TaskEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val item1 = oldList[oldPosition]
        val item2 = newList[newPosition]

        return item1.status == item2.status
                && item1.title == item2.title
                && item1.description == item2.description
    }

}