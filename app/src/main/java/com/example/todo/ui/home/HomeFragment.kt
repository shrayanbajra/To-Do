package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.ui.addtask.AddTaskBottomSheet
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var rvTasks: RecyclerView
    private val tasksAdapter = TasksAdapter(getOnItemClickListener())

    private fun getOnItemClickListener(): TasksAdapter.OnItemClickListener {
        return object : TasksAdapter.OnItemClickListener {

            override fun onItemClick(itemId: Int) {
                navigateToViewTaskFragment(itemId)
            }
        }
    }

    private fun navigateToViewTaskFragment(itemId: Int) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToViewTaskFragment(taskID = itemId)
        findNavController().navigate(action)
    }

    private lateinit var btnAddTask: FloatingActionButton

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

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
        rvTasks.adapter = tasksAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeTasks()

        btnAddTaskListener()
    }

    private fun observeTasks() {
        viewModel.getTasks().observe(viewLifecycleOwner, { tasks ->
            tasksAdapter.update(tasks)
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
