package com.example.todo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.db.task.TaskEntity

class TasksAdapter(
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val tasks = arrayListOf<TaskEntity>()

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

    fun update(tasks: List<TaskEntity>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    class TaskViewHolder(
        itemView: View,
        private val clickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val cbTask: TextView = itemView.findViewById(R.id.cb_task)

        fun bind(task: TaskEntity) {
            cbTask.text = task.title
            itemView.setOnClickListener { clickListener.onItemClick(task.id) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(itemId: Int)
    }
}