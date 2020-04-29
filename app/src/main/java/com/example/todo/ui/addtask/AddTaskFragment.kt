package com.example.todo.ui.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddTaskFragment : Fragment() {

    private lateinit var tilTaskTitle: TextInputLayout
    private lateinit var etTaskTitle: TextInputEditText

    private lateinit var tilTaskContent: TextInputLayout
    private lateinit var etTaskContent: TextInputEditText

    private lateinit var btnSubmitTask: Button

    private val viewModel by lazy {
        ViewModelProvider(this)[AddTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        tilTaskTitle = view.findViewById(R.id.til_task_title)
        etTaskTitle = view.findViewById(R.id.et_task_title)

        tilTaskContent = view.findViewById(R.id.til_task_content)
        etTaskContent = view.findViewById(R.id.et_task_content)

        btnSubmitTask = view.findViewById(R.id.btn_submit_task)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSubmitTask.setOnClickListener {

            // Clear previously set errors
            if (hasErrorSet(tilTaskTitle)) clearError(tilTaskTitle)
            if (hasErrorSet(tilTaskContent)) clearError(tilTaskContent)

            // Get inputs
            val taskTitle: String = etTaskTitle.text.toString().trim()
            val taskContent: String = etTaskContent.text.toString().trim()

            var hasInvalidField = false

            // Check for validity
            if (taskTitle.isBlank()) {
                tilTaskTitle.error = "Please enter task title"
                hasInvalidField = true
            }

            if (taskContent.isBlank()) {
                tilTaskContent.error = "Please enter task content"
                hasInvalidField = true
            }

            if (hasInvalidField) return@setOnClickListener

            // Insert task into database
            insertTask(taskTitle, taskContent)
            showSuccessMessage()

            clearInputFields()
        }
    }

    private fun clearError(taskInputLayout: TextInputLayout) {
        taskInputLayout.isErrorEnabled = false
    }

    private fun hasErrorSet(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.isErrorEnabled
    }

    private fun insertTask(taskTitle: String, taskContent: String) {
        val task = TaskEntity(taskTitle, taskContent)
        viewModel.insertTask(task)
    }

    private fun showSuccessMessage() {
        Toast.makeText(context, "Task inserted in Database", Toast.LENGTH_SHORT).show()
    }

    private fun clearInputFields() {
        etTaskTitle.text?.clear()
        etTaskContent.text?.clear()
    }
}
