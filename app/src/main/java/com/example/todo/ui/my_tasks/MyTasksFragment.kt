package com.example.todo.ui.my_tasks

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.FragmentMyTasksBinding
import com.example.todo.db.task.TaskEntity
import com.example.todo.di.app.utils.ViewModelProviderFactory
import com.example.todo.ui.add_task.AddTaskBottomSheet
import com.example.todo.ui.my_tasks.sort_by_bottom_sheet.CriteriaAdapter
import com.example.todo.ui.my_tasks.sort_by_bottom_sheet.SortByBottomSheet
import com.example.todo.utils.Constants
import com.example.todo.utils.SortingCriteria
import com.example.todo.utils.SortingOrder
import com.example.todo.utils.helpers.TasksHelper
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MyTasksFragment : DaggerFragment() {

    private var _binding: FragmentMyTasksBinding? = null
    private val mBinding get() = _binding!!

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

    @Inject
    lateinit var mSharedPref: SharedPreferences

    @Inject
    lateinit var mProviderFactory: ViewModelProviderFactory

    private val mViewModel: MyTasksViewModel by lazy {
        ViewModelProvider(this, mProviderFactory)[MyTasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        return mBinding.root
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

    private fun getOnCriteriaSelectedListener(): CriteriaAdapter.OnCriteriaSelectedListener {
        return object : CriteriaAdapter.OnCriteriaSelectedListener {

            override fun onSelected(title: String) {

                when (title) {
                    getString(R.string.alphabetically) -> {

                        val sortingCriteria = SortingCriteria.ALPHABETICALLY
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                    getString(R.string.completed) -> {

                        val sortingCriteria = SortingCriteria.COMPLETION_STATUS
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                    getString(R.string.date_added) -> {

                        val sortingCriteria = SortingCriteria.DATE_ADDED
                        saveSortingCriteriaInSharedPref(sortingCriteria = sortingCriteria)
                        sortTasks(sortingCriteria = sortingCriteria.value)

                    }
                }

            }

        }
    }

    private fun saveSortingCriteriaInSharedPref(sortingCriteria: SortingCriteria) {
        mSharedPref.edit {
            putString(Constants.KEY_SORTING_CRITERIA, sortingCriteria.value)
        }
    }

    private fun getAlphabeticallySortedTasks(
        sortingOrder: SortingOrder = SortingOrder.VALUE_ASCENDING
    ): ArrayList<TaskEntity> {

        val tasks = ArrayList<TaskEntity>(mTasksAdapter.getTasks())
        return TasksHelper.getAlphabeticallySortedTasks(tasks, sortingOrder)

    }

    private fun getTasksSortedByCompletionStatus(sortingOrder: SortingOrder = SortingOrder.VALUE_ASCENDING): MutableList<TaskEntity> {

        val tasks = ArrayList<TaskEntity>(mTasksAdapter.getTasks())
        return TasksHelper.getTasksSortedByCompletionStatus(tasks, sortingOrder)

    }

    private fun getTasksSortedByDateAdded(sortingOrder: SortingOrder = SortingOrder.VALUE_ASCENDING): MutableList<TaskEntity> {

        val tasks = ArrayList<TaskEntity>(mTasksAdapter.getTasks())
        return TasksHelper.getTasksSortedByDateAdded(tasks, sortingOrder)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvTasks()

    }

    private fun initRvTasks() {
        mBinding.rvRemainingTasks.apply {
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

    private fun updateSortingOrderIcon(sortingOrder: SortingOrder) {
        val imageResource: Int = if (sortingOrder == SortingOrder.VALUE_ASCENDING)
            R.drawable.ic_arrow_up
        else
            R.drawable.ic_arrow_down

        mBinding.ivTasksSortingOrderIcon.setImageResource(imageResource)
    }

    private fun sortingOrderClickListener() {
        mBinding.tvTasksSortingOrder.setOnClickListener(sortingOrderClickListener)
        mBinding.ivTasksSortingOrderIcon.setOnClickListener(sortingOrderClickListener)
    }

    private val sortingOrderClickListener = View.OnClickListener {

        val sortingOrder = getSortingOrderFromSharedPref()
        val updatedSortingOrder = getToggledSortingOrder(sortingOrder)

        setSortingOrderFromSharedPref(updatedSortingOrder)
        updateSortingOrderIcon(updatedSortingOrder)

        sortTasks()

    }

    private fun getSortingOrderFromSharedPref(): SortingOrder {
        val defaultValue = SortingOrder.VALUE_ASCENDING.value
        val value = mSharedPref.getString(Constants.KEY_SORTING_ORDER, defaultValue)
            ?: defaultValue

        val isAscending = value == SortingOrder.VALUE_ASCENDING.value
        return if (isAscending)
            SortingOrder.VALUE_ASCENDING
        else
            SortingOrder.VALUE_DESCENDING

    }

    private fun getToggledSortingOrder(sortingOrder: SortingOrder): SortingOrder {

        val isAscending = sortingOrder == SortingOrder.VALUE_ASCENDING
        return if (isAscending)
            SortingOrder.VALUE_DESCENDING
        else
            SortingOrder.VALUE_ASCENDING

    }

    private fun setSortingOrderFromSharedPref(sortingOrder: SortingOrder) {
        mSharedPref.edit {
            putString(Constants.KEY_SORTING_ORDER, sortingOrder.value)
        }
    }

    private fun observeTasks() {
        mViewModel.getTasks().observe(viewLifecycleOwner) { tasks ->

            if (tasks.isEmpty()) {

                showEmptyState()

            } else {

                showTasks()
                mTasksAdapter.setTasks(tasks)

                sortTasks()

            }

        }
    }

    private fun showEmptyState() {
        mBinding.apply {

            llEmptyState.visibility = View.VISIBLE

            val hiddenVisibility = View.GONE
            tvTasksSortingOrder.visibility = hiddenVisibility
            ivTasksSortingOrderIcon.visibility = hiddenVisibility
            rvRemainingTasks.visibility = hiddenVisibility

        }
    }

    private fun showTasks() {
        mBinding.apply {

            val shownVisibility = View.VISIBLE
            tvTasksSortingOrder.visibility = shownVisibility
            ivTasksSortingOrderIcon.visibility = shownVisibility
            rvRemainingTasks.visibility = shownVisibility

            llEmptyState.visibility = View.GONE
        }
    }

    private fun sortTasks(sortingCriteria: String? = null) {

        val sortingOrder = getSortingOrderFromSharedPref()
        val sortedTasks = when (sortingCriteria ?: getSortingCriterion()) {
            SortingCriteria.ALPHABETICALLY.value -> {

                updateTvSortingOrder(text = getString(R.string.sorted_alphabetically))
                getAlphabeticallySortedTasks(sortingOrder)

            }
            SortingCriteria.COMPLETION_STATUS.value -> {

                updateTvSortingOrder(text = getString(R.string.sorted_by_completion_status))
                getTasksSortedByCompletionStatus(sortingOrder)

            }
            SortingCriteria.DATE_ADDED.value -> {

                updateTvSortingOrder(text = getString(R.string.sorted_by_added_date))
                getTasksSortedByDateAdded(sortingOrder)

            }
            else -> { // Default is alphabetical sorting

                updateTvSortingOrder(text = getString(R.string.sorted_alphabetically))
                getAlphabeticallySortedTasks(sortingOrder)

            }
        }
        mTasksAdapter.setTasks(sortedTasks)
    }

    private fun getSortingCriterion(): String {
        return mSharedPref.getString(Constants.KEY_SORTING_CRITERIA, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING
    }

    private fun updateTvSortingOrder(text: String) {
        mBinding.tvTasksSortingOrder.text = text
    }

    private fun btnAddTaskListener() {
        mBinding.btnAddTask.setOnClickListener { showAddTaskBottomSheet() }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskBottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
