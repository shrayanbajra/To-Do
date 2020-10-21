package com.example.todo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.google.android.material.checkbox.MaterialCheckBox


class TasksAdapter(
    private val listener: TaskListener
) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val tasks = arrayListOf<TaskEntity>()

    fun setTasks(tasks: List<TaskEntity>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount() = tasks.size

    class TaskViewHolder(
        itemView: View,
        private val listener: TaskListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val cbTaskStatus: MaterialCheckBox = itemView.findViewById(R.id.cb_task_status)
        private val tvTaskTitle: TextView = itemView.findViewById(R.id.tv_task_title)

        fun bind(task: TaskEntity) {
            cbTaskStatus.isChecked = task.isTaskDone()
            cbTaskStatus.setOnCheckedChangeListener { _, isChecked ->
                task.setTaskStatus(isChecked = isChecked)
                listener.onCheckboxToggled(task = task)
            }

            tvTaskTitle.text = task.title
            tvTaskTitle.setOnClickListener { listener.onTaskClicked(task.id) }
        }
    }

    interface TaskListener {
        fun onTaskClicked(taskId: Int)
        fun onCheckboxToggled(task: TaskEntity)
    }
}