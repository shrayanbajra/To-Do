package com.example.todo.ui.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    private lateinit var tilTaskTitle: TextInputLayout
    private lateinit var tilTaskContent: TextInputLayout

    private lateinit var btnSave: Button

    private val viewModel by lazy { ViewModelProvider(this)[AddTaskViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        tilTaskTitle = view.findViewById(R.id.til_task_title)
        tilTaskContent = view.findViewById(R.id.til_task_content)

        btnSave = view.findViewById(R.id.btn_save)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSaveTaskListener()
    }

    private fun btnSaveTaskListener() {
        btnSave.setOnClickListener {

            clearErrorsInInputFields()

            val taskTitle: String = tilTaskTitle.editText?.text.toString().trim()
            val taskContent: String = tilTaskContent.editText?.text.toString().trim()

            if (taskTitle.isBlank()) {

                showErrorMessage()

            } else {

                insertTask(taskTitle, taskContent)
                showSuccessMessage()

                clearInputFields()

            }
        }
    }

    private fun clearErrorsInInputFields() {
        if (hasErrorSet(tilTaskTitle)) clearError(tilTaskTitle)
    }

    private fun hasErrorSet(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.isErrorEnabled
    }

    private fun clearError(taskInputLayout: TextInputLayout) {
        taskInputLayout.error = ""
    }

    private fun showErrorMessage() {
        tilTaskTitle.error = getString(R.string.please_enter_task_title)
    }

    private fun insertTask(taskTitle: String, taskContent: String) {
        val task = TaskEntity(taskTitle, taskContent)
        viewModel.insertTask(task)
    }

    private fun showSuccessMessage() {
        Toast.makeText(context, getString(R.string.task_saved), Toast.LENGTH_SHORT).show()
    }

    private fun clearInputFields() {
        tilTaskTitle.editText?.text?.clear()
        tilTaskContent.editText?.text?.clear()
    }
}
