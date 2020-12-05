package com.example.todo.ui.viewtask

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.todo.R
import com.example.todo.databinding.FragmentViewTaskBinding
import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.utils.Constants.NO_TASK_ID
import com.example.todo.utils.shortSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewTaskFragment : DaggerFragment() {

    private var _binding: FragmentViewTaskBinding? = null
    private val mBinding get() = _binding!!

    private var taskId: Int = NO_TASK_ID

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel by lazy {
        ViewModelProvider(this, providerFactory)[ViewTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentViewTaskBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskId = getTaskIdFromArgumentBundle()
        if (taskId == NO_TASK_ID)
            shortSnackbar(getString(R.string.couldnt_get_task_details))
        else
            observeTask(taskId)

    }

    private fun getTaskIdFromArgumentBundle(): Int {
        val args: ViewTaskFragmentArgs? = arguments?.let { ViewTaskFragmentArgs.fromBundle(it) }
        return args?.taskId ?: NO_TASK_ID
    }

    private fun observeTask(taskId: Int) {
        viewModel.getTask(taskId).observe(viewLifecycleOwner, { task ->
            if (task == null)
                shortSnackbar(getString(R.string.couldnt_get_task_details))
            else
                setValuesInTitleAndDescription(task)
        })
    }

    private fun setValuesInTitleAndDescription(task: TaskEntity) {
        mBinding.etTaskTitle.setText(task.title)
        mBinding.etTaskDescription.setText(task.description)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_view_task_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.item_save_task -> {
                proceedToSaveTask()
                true
            }
            R.id.item_delete_task -> {
                deleteTask()
                shortSnackbar("Task Deleted")
                findNavController().popBackStack()
                true
            }
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item, requireView().findNavController()
                ) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun proceedToSaveTask() {

        val taskTitle = mBinding.etTaskTitle.text.toString().trim()
        if (taskTitle.isBlank())
            shortSnackbar(getString(R.string.title_cant_be_blank))
        else {
            val taskDescription = mBinding.etTaskDescription.text.toString().trim()
            saveChanges(taskTitle, taskDescription)
        }

    }

    private fun saveChanges(taskTitle: String, taskDescription: String) {
        updateTask(taskTitle, taskDescription)
        shortSnackbar(getString(R.string.changes_saved))
    }

    private fun updateTask(taskTitle: String, taskDescription: String) {
        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = taskTitle,
            description = taskDescription
        )
        task.id = taskId
        viewModel.updateTask(task)
    }

    private fun deleteTask() {
        viewModel.deleteTask(taskId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

