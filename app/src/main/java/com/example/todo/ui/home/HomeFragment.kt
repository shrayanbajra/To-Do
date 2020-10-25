package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private lateinit var mTvTasksTitle: TextView
    private lateinit var mRvTasks: RecyclerView
    private val mTasksAdapter = TasksAdapter(getTaskClickListener())

    private fun getTaskClickListener(): TasksAdapter.TaskListener {
        return object : TasksAdapter.TaskListener {

            override fun onTaskClicked(taskId: Int) {
                navigateToViewTaskFragment(taskId)
            }

            override fun onCheckboxToggled(task: TaskEntity, adapterPosition: Int) {
                mViewModel.updateTask(task = task)
            }
        }
    }

    private fun navigateToViewTaskFragment(itemId: Int) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToViewTaskFragment(taskId = itemId)
        findNavController().navigate(action)
    }

    private lateinit var mBtnAddTask: FloatingActionButton

    @Inject
    lateinit var mProviderFactory: ViewModelProviderFactory

    private val mViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, mProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvTasksTitle = view.findViewById(R.id.tv_tasks_title)
        initRvTasks(view)

        mBtnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRvTasks(view: View) {
        mRvTasks = view.findViewById(R.id.rv_remaining_tasks)
        mRvTasks.layoutManager = LinearLayoutManager(context)
        mRvTasks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mRvTasks.adapter = mTasksAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeTasks()

        btnAddTaskListener()
    }

    private fun observeTasks() {
        mViewModel.getAll().observe(viewLifecycleOwner, { tasks ->

            if (tasks.isEmpty()) {
                hideRemainingTasksSection()
            } else {
                showRemainingTasksSection()
                mTasksAdapter.setTasks(tasks)
            }

        })
    }

    private fun hideRemainingTasksSection() {
        mTvTasksTitle.visibility = View.GONE
        mRvTasks.visibility = View.GONE
    }

    private fun showRemainingTasksSection() {
        mTvTasksTitle.visibility = View.VISIBLE
        mRvTasks.visibility = View.VISIBLE
    }

    private fun btnAddTaskListener() {
        mBtnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.javaClass.name)
    }
}
