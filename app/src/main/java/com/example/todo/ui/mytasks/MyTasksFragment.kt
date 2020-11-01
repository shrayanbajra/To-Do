package com.example.todo.ui.mytasks

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ImageView
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

    private lateinit var mTvSortingOrder: TextView
    private lateinit var mIvSortingOrderIcon: ImageView

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

                    saveSortingCriteriaInSharedPref(value = Constants.VALUE_ALPHABETICALLY)
                    val sortingOrder = getSortingOrderFromSharedPref()
                    val sortedTasks = getAlphabeticallySortedTasks(sortingOrder)
                    mTasksAdapter.setTasks(sortedTasks)

                } else if (title == getString(R.string.completed)) {

                    saveSortingCriteriaInSharedPref(value = Constants.VALUE_COMPLETED)

                }

            }

        }
    }

    private fun saveSortingCriteriaInSharedPref(value: String) {
        val editor = mSharedPref?.edit()
        editor?.putString(Constants.KEY_SORTING_CRITERIA, value)
        editor?.apply()
    }

    private fun getAlphabeticallySortedTasks(sortingOrder: String = Constants.VALUE_ASCENDING): MutableList<TaskEntity> {
        val tasks = mTasksAdapter.getTasks().toMutableList()
        tasks.sortWith { task1, task2 ->

            val firstTitle = task1.title
            val secondTitle = task2.title

            val isAscending = sortingOrder == Constants.VALUE_ASCENDING
            if (isAscending)
                firstTitle.compareTo(secondTitle)
            else
                secondTitle.compareTo(firstTitle)

        }
        return tasks
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvTasksTitle = view.findViewById(R.id.tv_tasks_title)

        mTvSortingOrder = view.findViewById(R.id.tv_tasks_sorting_order)
        mIvSortingOrderIcon = view.findViewById(R.id.iv_tasks_sorting_order_icon)

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

        val sortingOrder = getSortingOrderFromSharedPref()
        updateSortingOrderIcon(sortingOrder)

        sortingOrderClickListener()

        observeTasks()

        btnAddTaskListener()
    }

    private fun updateSortingOrderIcon(sortingOrder: String) {
        val imageResource: Int = if (sortingOrder == Constants.VALUE_ASCENDING)
            R.drawable.ic_arrow_up
        else
            R.drawable.ic_arrow_down

        mIvSortingOrderIcon.setImageResource(imageResource)
    }

    private fun sortingOrderClickListener() {
        mTvSortingOrder.setOnClickListener(sortingOrderClickListener)
        mIvSortingOrderIcon.setOnClickListener(sortingOrderClickListener)
    }

    private val sortingOrderClickListener = View.OnClickListener {

        val sortingOrder = getSortingOrderFromSharedPref()
        val updatedSortingOrder = getToggledSortingOrder(sortingOrder)

        setSortingOrderFromSharedPref(updatedSortingOrder)
        updateSortingOrderIcon(updatedSortingOrder)

        sortTasks()

    }

    private fun getSortingOrderFromSharedPref(): String {
        return mSharedPref?.getString(Constants.KEY_SORTING_ORDER, Constants.VALUE_ASCENDING)
            ?: Constants.VALUE_ASCENDING
    }

    private fun getToggledSortingOrder(sortingOrder: String): String {
        return if (sortingOrder == Constants.VALUE_ASCENDING) {

            Constants.VALUE_DESCENDING

        } else {

            Constants.VALUE_ASCENDING

        }
    }

    private fun setSortingOrderFromSharedPref(value: String) {
        val editor = mSharedPref?.edit()
        editor?.putString(Constants.KEY_SORTING_ORDER, value)
        editor?.apply()
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
        val sortingCriterion = getSortingCriterion()
        if (sortingCriterion == Constants.VALUE_ALPHABETICALLY) {

            val sortingOrder = getSortingOrderFromSharedPref()
            val sortedTasks = getAlphabeticallySortedTasks(sortingOrder)
            mTasksAdapter.setTasks(sortedTasks)

        }
    }

    private fun getSortingCriterion(): String {
        return mSharedPref?.getString(Constants.KEY_SORTING_CRITERIA, Constants.EMPTY_STRING)
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
