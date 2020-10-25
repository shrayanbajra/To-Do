package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.db.task.TaskEntity
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.ui.addtask.AddTaskBottomSheet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    private lateinit var rvRemainingTasks: RecyclerView
    private val remainingTasksAdapter = TasksAdapter(getOnItemClickListener())

    private lateinit var rvCompletedTasks: RecyclerView
    private val completedTasksAdapter = TasksAdapter(getOnItemClickListener())

    private fun getOnItemClickListener(): TasksAdapter.TaskListener {
        return object : TasksAdapter.TaskListener {

            override fun onTaskClicked(taskId: Int) {
                navigateToViewTaskFragment(taskId)
            }

            override fun onCheckboxToggled(task: TaskEntity) {
                viewModel.updateTask(task = task)
            }
        }
    }

    private fun navigateToViewTaskFragment(itemId: Int) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToViewTaskFragment(taskId = itemId)
        findNavController().navigate(action)
    }

    private lateinit var btnAddTask: FloatingActionButton

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, providerFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvRemainingTasks(view)
        initRvCompletedTasks(view)

        btnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRvRemainingTasks(view: View) {
        rvRemainingTasks = view.findViewById(R.id.rv_remaining_tasks)
        rvRemainingTasks.layoutManager = LinearLayoutManager(context)
        rvRemainingTasks.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvRemainingTasks.adapter = remainingTasksAdapter
    }

    private fun initRvCompletedTasks(view: View) {
        rvCompletedTasks = view.findViewById(R.id.rv_completed_tasks)
        rvCompletedTasks.layoutManager = LinearLayoutManager(context)
        rvCompletedTasks.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvCompletedTasks.adapter = completedTasksAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeRemainingTasks()
        observeCompletedTasks()

        btnAddTaskListener()
    }

    private fun observeRemainingTasks() {
        viewModel.getRemainingTasks().observe(viewLifecycleOwner, { tasks ->
            remainingTasksAdapter.setTasks(tasks)
        })
    }

    private fun observeCompletedTasks() {
        viewModel.getCompletedTasks().observe(viewLifecycleOwner, { tasks ->
            completedTasksAdapter.setTasks(tasks)
        })
    }

    private fun btnAddTaskListener() {
        btnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.javaClass.name)
    }
}
