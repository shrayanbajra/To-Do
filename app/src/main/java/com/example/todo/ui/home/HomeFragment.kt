package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
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

    private lateinit var mTvRemainingTasksTitle: TextView
    private lateinit var mRvRemainingTasks: RecyclerView
    private val mRemainingTasksAdapter = TasksAdapter(getRemainingTaskClickListener())

    private lateinit var mTvCompletedTasksTitle: TextView
    private lateinit var mRvCompletedTasks: RecyclerView
    private val mCompletedTasksAdapter = TasksAdapter(getCompletedTaskClickListener())

    private fun getRemainingTaskClickListener(): TasksAdapter.TaskListener {
        return object : TasksAdapter.TaskListener {

            override fun onTaskClicked(taskId: Int) {
                navigateToViewTaskFragment(taskId)
            }

            override fun onCheckboxToggled(task: TaskEntity, adapterPosition: Int) {
                mViewModel.updateTask(task = task)
            }
        }
    }

    private fun getCompletedTaskClickListener(): TasksAdapter.TaskListener {
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

        mTvRemainingTasksTitle = view.findViewById(R.id.tv_remaining_tasks_title)
        initRvRemainingTasks(view)

        mTvCompletedTasksTitle = view.findViewById(R.id.tv_completed_tasks_title)
        initRvCompletedTasks(view)

        mBtnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRvRemainingTasks(view: View) {
        mRvRemainingTasks = view.findViewById(R.id.rv_remaining_tasks)
        mRvRemainingTasks.layoutManager = LinearLayoutManager(context)
        mRvRemainingTasks.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mRvRemainingTasks.adapter = mRemainingTasksAdapter
    }

    private fun initRvCompletedTasks(view: View) {
        mRvCompletedTasks = view.findViewById(R.id.rv_completed_tasks)
        mRvCompletedTasks.layoutManager = LinearLayoutManager(context)
        mRvCompletedTasks.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        mRvCompletedTasks.adapter = mCompletedTasksAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeRemainingTasks()
        observeCompletedTasks()

        btnAddTaskListener()
    }

    private fun observeRemainingTasks() {
        mViewModel.getRemainingTasks().observe(viewLifecycleOwner, mRemainingTasksObserver)
    }

    private val mRemainingTasksObserver = Observer<List<TaskEntity>> { tasks ->

        if (tasks.isEmpty()) {
            hideRemainingTasksSection()
        } else {
            showRemainingTasksSection()
            mRemainingTasksAdapter.setTasks(tasks)
        }

    }

    private fun hideRemainingTasksSection() {
        mTvRemainingTasksTitle.visibility = View.GONE
        mRvRemainingTasks.visibility = View.GONE
    }

    private fun showRemainingTasksSection() {
        mTvRemainingTasksTitle.visibility = View.VISIBLE
        mRvRemainingTasks.visibility = View.VISIBLE
    }

    private fun observeCompletedTasks() {
        mViewModel.getCompletedTasks().observe(viewLifecycleOwner, mCompletedTasksObserver)
    }

    private val mCompletedTasksObserver = Observer<List<TaskEntity>> { tasks ->

        if (tasks.isEmpty()) {
            hideCompletedTasksSection()
        } else {
            showCompletedTasksSection()
            mCompletedTasksAdapter.setTasks(tasks)
        }

    }

    private fun hideCompletedTasksSection() {
        mTvCompletedTasksTitle.visibility = View.GONE
        mRvCompletedTasks.visibility = View.GONE
    }

    private fun showCompletedTasksSection() {
        mTvCompletedTasksTitle.visibility = View.VISIBLE
        mRvCompletedTasks.visibility = View.VISIBLE
    }

    private fun btnAddTaskListener() {
        mBtnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.javaClass.name)
    }
}
