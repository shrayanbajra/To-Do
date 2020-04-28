package com.example.todo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.db.task.TaskEntity

class HomeAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {

    private val tasks = arrayListOf<TaskEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return MainViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, listener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun update(tasks: List<TaskEntity>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        val onItemClickListener: OnItemClickListener = listener

        fun bind(task: TaskEntity, listener: OnItemClickListener) {
            taskTitle.text = task.taskTitle
            itemView.setOnClickListener {
                listener.onItemClick(task.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(itemID: Int)
    }
}