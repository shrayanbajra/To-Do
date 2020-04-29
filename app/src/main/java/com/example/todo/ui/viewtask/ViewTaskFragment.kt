package com.example.todo.ui.viewtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.db.task.TaskEntity

class ViewTaskFragment : Fragment() {

    companion object {
        private const val NO_ID = -1
    }

    private var taskID: Int = NO_ID

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskContent: EditText

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

        val args: ViewTaskFragmentArgs? = arguments?.let { ViewTaskFragmentArgs.fromBundle(it) }
        args?.let {
            taskID = it.taskID
        }

        etTaskTitle = view.findViewById(R.id.et_task_title_view)
        etTaskContent = view.findViewById(R.id.et_task_content)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getTask()
        observeTask()
    }

    private fun getTask() {
        if (taskID == NO_ID) {
            Toast.makeText(context, "Couldn't get task details", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.getTask(taskID)
    }

    private fun observeTask() {
        viewModel.getTaskLiveData().observe(viewLifecycleOwner, Observer { task ->
            populateEditTexts(task)
        })
    }

    private fun populateEditTexts(task: TaskEntity) {
        etTaskTitle.setText(task.taskTitle)
        etTaskContent.setText(task.taskContent)
    }
}

