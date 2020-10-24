package com.example.todo.ui.viewtask

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.utils.shortSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewTaskFragment : DaggerFragment() {

    companion object {
        private const val NO_TASK_ID = -1
    }

    private var taskId: Int = NO_TASK_ID

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel by lazy {
        ViewModelProvider(this, providerFactory)[ViewTaskViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        etTaskTitle = view.findViewById(R.id.et_task_title_view)
        etTaskDescription = view.findViewById(R.id.et_task_description)
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
                populateEditTexts(task)
        })
    }

    private fun populateEditTexts(task: TaskEntity) {
        etTaskTitle.setText(task.title)
        etTaskDescription.setText(task.description)
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
                navigateToHomeFragment()
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

        val taskTitle = etTaskTitle.text.toString().trim()
        if (taskTitle.isBlank())
            shortSnackbar(getString(R.string.title_cant_be_blank))
        else {
            val taskDescription = etTaskDescription.text.toString().trim()
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

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_viewTaskFragment_to_homeFragment)
    }
}

