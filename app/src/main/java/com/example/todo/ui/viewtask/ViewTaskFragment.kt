package com.example.todo.ui.viewtask

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.example.todo.utils.shortToast

class ViewTaskFragment : Fragment() {

    companion object {
        private const val NO_TASK_ID = -1
    }

    private var taskId: Int = NO_TASK_ID

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskContent: EditText

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewTaskViewModel::class.java]
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
        etTaskContent = view.findViewById(R.id.et_task_description)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskId = getTaskFromArgumentBundle()
        if (taskId == NO_TASK_ID) {
            shortToast("Couldn't get task details")
        } else {
            observeTask(taskId)
        }

    }

    private fun getTaskFromArgumentBundle(): Int {
        val args: ViewTaskFragmentArgs? = arguments?.let { ViewTaskFragmentArgs.fromBundle(it) }
        return args?.taskID ?: NO_TASK_ID
    }

    private fun observeTask(taskId: Int) {
        viewModel.getTask(taskId).observe(viewLifecycleOwner, { task ->
            if (task == null)
                shortToast("Couldn't get task details")
            else
                populateEditTexts(task)
        })
    }

    private fun populateEditTexts(task: TaskEntity) {
        etTaskTitle.setText(task.taskTitle)
        etTaskContent.setText(task.taskContent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_view_task_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.item_save_task -> {
                saveChanges()
                true
            }
            R.id.item_delete_task -> {
                deleteTask()
                true
            }
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item, requireView().findNavController()
                ) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveChanges() {

        val taskTitle = etTaskTitle.text.toString().trim()
        val taskContent = etTaskContent.text.toString().trim()

        if (taskTitle.isBlank()) {
            shortToast("Title can't be blank")
            return
        }

        updateTask(taskTitle, taskContent)
        shortToast("Changes Saved")
    }

    private fun updateTask(taskTitle: String, taskContent: String) {
        val task = TaskEntity(taskTitle, taskContent)
        task.id = taskId
        viewModel.updateTask(task)
    }

    private fun deleteTask() {
        viewModel.deleteTask(taskId)
        shortToast("Task Deleted")
        navigateToHomeFragment()
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_viewTaskFragment_to_homeFragment)
    }
}

