package com.example.todo.ui.viewtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R

class ViewTaskFragment : Fragment() {

    private lateinit var etTaskTitle: EditText

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTaskTitle = view.findViewById(R.id.et_task_title_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getTask()
        observeTask()
    }

    private fun getTask() {
        viewModel.getTask(1)
    }

    private fun observeTask() {
        viewModel.getTaskLiveData().observe(viewLifecycleOwner, Observer { task ->
            etTaskTitle.setText(task.taskTitle)
        })
    }
}
