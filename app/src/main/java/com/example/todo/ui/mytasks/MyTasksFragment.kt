package com.example.todo.ui.mytasks

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
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
import com.example.todo.ui.mytasks.sortbybottomsheet.OnCriteriaSelectedListener
import com.example.todo.ui.mytasks.sortbybottomsheet.SortByBottomSheet
import com.example.todo.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MyTasksFragment : DaggerFragment() {

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

    private fun navigateToViewTaskFragment(taskId: Int) {
        val action = MyTasksFragmentDirections
            .actionMyTasksFragmentToViewTaskFragment(taskId = taskId)
        findNavController().navigate(action)
    }

    private lateinit var mBtnAddTask: FloatingActionButton

    private val mSharedPref: SharedPreferences? by lazy {
        activity?.getSharedPreferences(Constants.PREF_MY_TASKS, MODE_PRIVATE)
    }

    @Inject
    lateinit var mProviderFactory: ViewModelProviderFactory

    private val mViewModel: MyTasksViewModel by lazy {
        ViewModelProvider(this, mProviderFactory)[MyTasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_my_tasks, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_my_tasks_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (item.itemId == R.id.item_sort) {

            showSortByBottomSheet()
            true

        } else
            super.onOptionsItemSelected(item)
    }

    private fun showSortByBottomSheet() {
        val sortByBottomSheet = SortByBottomSheet(getOnCriteriaSelectedListener())
        sortByBottomSheet.show(activity?.supportFragmentManager!!, sortByBottomSheet.tag)
    }

    private fun getOnCriteriaSelectedListener(): OnCriteriaSelectedListener {
        return object : OnCriteriaSelectedListener {

            override fun onSelected(title: String) {

                if (title == getString(R.string.alphabetically)) {

                    saveSortingOrderInSharedPref(value = Constants.VALUE_ALPHABETICALLY)
                    val sortedTasks = getSortedTasks()
                    mTasksAdapter.setTasks(sortedTasks)

                } else if (title == getString(R.string.completed)) {

                    saveSortingOrderInSharedPref(value = Constants.VALUE_COMPLETED)

                }

            }

        }
    }

    private fun saveSortingOrderInSharedPref(value: String) {
        val editor = mSharedPref?.edit()
        editor?.putString(Constants.KEY_SORTING_ORDER, value)
        editor?.apply()
    }

    private fun getSortedTasks(): MutableList<TaskEntity> {
        val tasks = mTasksAdapter.getTasks().toMutableList()
        tasks.sortWith { task1, task2 ->
            val firstTitle = task1.title
            val secondTitle = task2.title
            firstTitle.compareTo(secondTitle)
        }
        return tasks
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvTasksTitle = view.findViewById(R.id.tv_tasks_title)
        initRvTasks(view)

        mBtnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRvTasks(view: View) {
        mRvTasks = view.findViewById(R.id.rv_remaining_tasks)
        mRvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = mTasksAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeTasks()

        btnAddTaskListener()
    }

    private fun observeTasks() {
        mViewModel.getTasks().observe(viewLifecycleOwner, { tasks ->

            if (tasks.isEmpty()) {

                hideMyTasksSection()

            } else {

                showMyTasksSection()
                mTasksAdapter.setTasks(tasks)

                sortTasks()

            }

        })
    }

    private fun hideMyTasksSection() {
        mTvTasksTitle.visibility = View.GONE
        mRvTasks.visibility = View.GONE
    }

    private fun showMyTasksSection() {
        mTvTasksTitle.visibility = View.VISIBLE
        mRvTasks.visibility = View.VISIBLE
    }

    private fun sortTasks() {
        val sortingOrder = getSortingOrder()
        if (sortingOrder == Constants.VALUE_ALPHABETICALLY) {

            val sortedTasks = getSortedTasks()
            mTasksAdapter.setTasks(sortedTasks)

        }
    }

    private fun getSortingOrder(): String {
        return mSharedPref?.getString(Constants.KEY_SORTING_ORDER, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING
    }

    private fun btnAddTaskListener() {
        mBtnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.javaClass.name)
    }
}
