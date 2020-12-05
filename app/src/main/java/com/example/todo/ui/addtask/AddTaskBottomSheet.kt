package com.example.todo.ui.addtask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.utils.closeBottomSheet
import com.example.todo.utils.shortToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    private lateinit var tilTaskTitle: TextInputLayout
    private lateinit var tilTaskDescription: TextInputLayout

    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel by lazy {
        ViewModelProvider(this, providerFactory)[AddTaskViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_task, container, false)
    }

    override fun getTheme(): Int {
        return R.style.Custom_RoundedTop_BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        tilTaskTitle = view.findViewById(R.id.til_task_title)
        tilTaskDescription = view.findViewById(R.id.til_task_description)

        btnSave = view.findViewById(R.id.btn_save)
        btnCancel = view.findViewById(R.id.btn_cancel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSaveListener()
        btnCancelListener()
    }

    private fun btnSaveListener() {
        btnSave.setOnClickListener {

            clearErrorsInInputFields()

            val title: String = tilTaskTitle.editText?.text.toString().trim()
            val description: String = tilTaskDescription.editText?.text.toString().trim()

            if (title.isBlank()) {

                showErrorMessage()

            } else {

                insertTask(title, description)
                showSuccessMessage()

                closeBottomSheet()
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

    private fun clearError(textInputLayout: TextInputLayout) {
        textInputLayout.error = ""
    }

    private fun showErrorMessage() {
        tilTaskTitle.error = getString(R.string.please_enter_task_title)
    }

    private fun insertTask(title: String, description: String) {
        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = title,
            description = description,
            dateAdded = System.currentTimeMillis()
        )
        viewModel.insertTask(task)
    }

    private fun showSuccessMessage() {
        shortToast(getString(R.string.task_saved))
    }

    private fun clearInputFields() {
        tilTaskTitle.editText?.text?.clear()
        tilTaskDescription.editText?.text?.clear()
    }

    private fun btnCancelListener() {
        btnCancel.setOnClickListener { closeBottomSheet() }
    }

}