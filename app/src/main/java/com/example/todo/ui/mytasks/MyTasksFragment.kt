package com.example.todo.ui.mytasks

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

    @Inject
    lateinit var mSharedPref: SharedPreferences

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

                when (title) {
                    getString(R.string.alphabetically) -> {

                        val sortingCriteria = Constants.SortingCriteria.VALUE_ALPHABETICALLY
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                    getString(R.string.completed) -> {

                        val sortingCriteria = Constants.SortingCriteria.VALUE_COMPLETED
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                    getString(R.string.date_added) -> {

                        val sortingCriteria = Constants.SortingCriteria.VALUE_DATE_ADDED
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                }

            }

        }
    }

    private fun saveSortingCriteriaInSharedPref(sortingCriteria: Constants.SortingCriteria) {
        val editor = mSharedPref.edit()
        editor?.putString(Constants.KEY_SORTING_CRITERIA, sortingCriteria.value)
        editor?.apply()
    }

    private fun getAlphabeticallySortedTasks(sortingOrder: Constants.SortingOrder = Constants.SortingOrder.VALUE_ASCENDING): MutableList<TaskEntity> {
        val tasks = mTasksAdapter.getTasks().toMutableList()
        tasks.sortWith { task1, task2 ->

            val firstTitle = task1.title
            val secondTitle = task2.title

            val isAscending = sortingOrder == Constants.SortingOrder.VALUE_ASCENDING
            if (isAscending)
                firstTitle.compareTo(secondTitle)
            else
                secondTitle.compareTo(firstTitle)

        }
        return tasks
    }

    private fun getTasksSortedByCompletionStatus(sortingOrder: Constants.SortingOrder = Constants.SortingOrder.VALUE_ASCENDING): MutableList<TaskEntity> {
        val tasks = mTasksAdapter.getTasks().toMutableList()
        tasks.sortWith { task1, task2 ->

            val firstTaskStatus = task1.isTaskDone()
            val secondTaskStatus = task2.isTaskDone()

            val isAscending = sortingOrder == Constants.SortingOrder.VALUE_ASCENDING
            if (isAscending)
                firstTaskStatus.compareTo(secondTaskStatus)
            else
                secondTaskStatus.compareTo(firstTaskStatus)

        }
        return tasks
    }

    private fun getTasksSortedByDateAdded(sortingOrder: Constants.SortingOrder = Constants.SortingOrder.VALUE_ASCENDING): MutableList<TaskEntity> {
        val tasks = mTasksAdapter.getTasks().toMutableList()
        tasks.sortWith { task1, task2 ->

            val firstTaskAddedDate = task1.dateAdded
            val secondTaskAddedDate = task2.dateAdded

            val isAscending = sortingOrder == Constants.SortingOrder.VALUE_ASCENDING
            if (isAscending)
                firstTaskAddedDate.compareTo(secondTaskAddedDate)
            else
                secondTaskAddedDate.compareTo(firstTaskAddedDate)

        }
        return tasks
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun updateSortingOrderIcon(sortingOrder: Constants.SortingOrder) {
        val imageResource: Int = if (sortingOrder == Constants.SortingOrder.VALUE_ASCENDING)
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

    private fun getSortingOrderFromSharedPref(): Constants.SortingOrder {
        val value = mSharedPref.getString(
            Constants.KEY_SORTING_ORDER,
            Constants.SortingOrder.VALUE_ASCENDING.value
        )
            ?: Constants.SortingOrder.VALUE_ASCENDING.value

        val isAscending = value == Constants.SortingOrder.VALUE_ASCENDING.value
        return if (isAscending)
            Constants.SortingOrder.VALUE_ASCENDING
        else
            Constants.SortingOrder.VALUE_DESCENDING

    }

    private fun getToggledSortingOrder(sortingOrder: Constants.SortingOrder): Constants.SortingOrder {
        val isAscending = sortingOrder == Constants.SortingOrder.VALUE_ASCENDING
        return if (isAscending) {

            Constants.SortingOrder.VALUE_DESCENDING

        } else {

            Constants.SortingOrder.VALUE_ASCENDING

        }
    }

    private fun setSortingOrderFromSharedPref(sortingOrder: Constants.SortingOrder) {
        val editor = mSharedPref.edit()
        editor?.putString(Constants.KEY_SORTING_ORDER, sortingOrder.value)
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
        mTvSortingOrder.visibility = View.GONE
        mIvSortingOrderIcon.visibility = View.GONE
        mRvTasks.visibility = View.GONE
    }

    private fun showMyTasksSection() {
        mTvSortingOrder.visibility = View.VISIBLE
        mIvSortingOrderIcon.visibility = View.VISIBLE
        mRvTasks.visibility = View.VISIBLE
    }

    private fun sortTasks(sortingCriteria: String? = null) {
        when (sortingCriteria ?: getSortingCriterion()) {
            Constants.SortingCriteria.VALUE_ALPHABETICALLY.value -> {

                val sortingOrder = getSortingOrderFromSharedPref()
                val sortedTasks = getAlphabeticallySortedTasks(sortingOrder)
                mTasksAdapter.setTasks(sortedTasks)

                updateTvSortingOrder(text = getString(R.string.sorted_alphabetically))

            }
            Constants.SortingCriteria.VALUE_COMPLETED.value -> {

                val sortingOrder = getSortingOrderFromSharedPref()
                val sortedTasks = getTasksSortedByCompletionStatus(sortingOrder)
                mTasksAdapter.setTasks(sortedTasks)

                updateTvSortingOrder(text = getString(R.string.sorted_by_completion_status))

            }
            Constants.SortingCriteria.VALUE_DATE_ADDED.value -> {

                val sortingOrder = getSortingOrderFromSharedPref()
                val sortedTasks = getTasksSortedByDateAdded(sortingOrder)
                mTasksAdapter.setTasks(sortedTasks)

                updateTvSortingOrder(text = getString(R.string.sorted_by_added_date))

            }
        }
    }

    private fun getSortingCriterion(): String {
        return mSharedPref.getString(Constants.KEY_SORTING_CRITERIA, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING
    }

    private fun updateTvSortingOrder(text: String) {
        mTvSortingOrder.text = text
    }

    private fun btnAddTaskListener() {
        mBtnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.javaClass.name)
    }
}
