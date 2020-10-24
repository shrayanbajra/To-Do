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
import com.example.todo.di.app.ViewModelProviderFactory
import com.example.todo.ui.addtask.AddTaskBottomSheet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var rvTasks: RecyclerView
    private val tasksAdapter = TasksAdapter(getOnItemClickListener())

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

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvTasks(view)

        btnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRvTasks(view: View) {
        rvTasks = view.findViewById(R.id.rv_tasks)
        rvTasks.layoutManager = LinearLayoutManager(context)
        rvTasks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvTasks.adapter = tasksAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, providerFactory)[HomeViewModel::class.java]
        observeTasks()

        btnAddTaskListener()
    }

    private fun observeTasks() {
        viewModel.getTasks().observe(viewLifecycleOwner, { tasks ->
            tasksAdapter.setTasks(tasks)
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
