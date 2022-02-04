package com.example.todo.ui.add_task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.BottomSheetAddTaskBinding
import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.framework.extensions.*
import com.example.todo.utils.Logger
import com.example.todo.utils.helpers.DateFormatter
import com.example.todo.utils.helpers.DatePickerHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    private val mBinding get() = _binding!!
    private var _binding: BottomSheetAddTaskBinding? = null

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private var dueDate: Date? = null

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
    ): View {
        _binding = BottomSheetAddTaskBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setDueDateAsToday()
        btnDueDateListener()

        btnSaveListener()
        btnCancelListener()

    }

    private fun setDueDateAsToday() {
        dueDate = Date()
        mBinding.btnDueDate.text = DateFormatter().getFormattedDueDate(
            context = requireContext(), date = Date()
        )
    }

    private fun btnDueDateListener() {
        mBinding.btnDueDate.setOnClickListener {

            val dueDateAsCalendar = Calendar.getInstance().apply {
                timeInMillis = dueDate?.time ?: System.currentTimeMillis()
            }

            showDueDatePicker(dueDateAsCalendar)

        }
    }

    private fun showDueDatePicker(dueDateAsCalendar: Calendar) {
        val datePicker = DatePickerHelper().getDueDatePicker(
            context = requireContext(),
            calendar = dueDateAsCalendar
        ) { _, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.setYear(year)
            calendar.setMonth(month)
            calendar.setDayOfMonth(dayOfMonth)

            dueDate = Date(calendar.timeInMillis)
            val formattedDueDate = DateFormatter().getFormattedDueDate(
                context = requireContext(), date = dueDate ?: Date()
            )
            mBinding.btnDueDate.text = formattedDueDate

            Logger.d("$dayOfMonth $month $year")

        }
        datePicker.show()
    }

    private fun btnSaveListener() {
        mBinding.btnSave.setOnClickListener {

            clearErrorsInInputFields()

            val title: String = mBinding.tilTaskTitle.editText?.text.toString().trim()
            val description: String = mBinding.tilTaskDescription.editText?.text.toString().trim()

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
        if (hasErrorSet(mBinding.tilTaskTitle)) clearError(mBinding.tilTaskTitle)
    }

    private fun hasErrorSet(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.isErrorEnabled
    }

    private fun clearError(textInputLayout: TextInputLayout) {
        textInputLayout.error = ""
    }

    private fun showErrorMessage() {
        mBinding.tilTaskTitle.error = getString(R.string.please_enter_task_title)
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

    private fun showSuccessMessage() = shortToast(getString(R.string.task_saved))

    private fun clearInputFields() {
        mBinding.tilTaskTitle.editText?.text?.clear()
        mBinding.tilTaskDescription.editText?.text?.clear()
    }

    private fun btnCancelListener() {
        mBinding.btnCancel.setOnClickListener { closeBottomSheet() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}