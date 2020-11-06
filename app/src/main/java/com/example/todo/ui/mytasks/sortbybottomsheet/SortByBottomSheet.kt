package com.example.todo.ui.mytasks.sortbybottomsheet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Criteria
import com.example.todo.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortByBottomSheet(private val selectedListener: OnCriteriaSelectedListener) :
    BottomSheetDialogFragment() {

    private lateinit var mRvCriteria: RecyclerView
    private val mCriteriaAdapter = CriteriaAdapter(getOnCriteriaSelectedListener())

    private fun getOnCriteriaSelectedListener(): OnCriteriaSelectedListener {
        return object : OnCriteriaSelectedListener {

            override fun onSelected(title: String) {
                selectedListener.onSelected(title)
                closeBottomSheet()
            }

        }
    }

    private lateinit var mIvClose: ImageView

    private val mSharedPref: SharedPreferences? by lazy {
        activity?.getSharedPreferences(Constants.PREF_MY_TASKS, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_sort_by, container, false)
    }

    override fun getTheme(): Int {
        return R.style.Custom_RoundedTop_BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mIvClose = view.findViewById(R.id.iv_close)
        initRvCriteria(view)

    }

    private fun initRvCriteria(view: View) {
        mRvCriteria = view.findViewById(R.id.rv_criteria)
        mRvCriteria.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCriteriaAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mIvClose.setOnClickListener { closeBottomSheet() }

        val criteria = getCriteria()
        mCriteriaAdapter.setCriteria(criteria)

    }

    private fun closeBottomSheet() {
        dialog?.dismiss()
    }

    private fun getSortingCriteriaFromSharedPref(): Constants.SortingCriteria {

        val defaultCriteria = Constants.SortingCriteria.VALUE_ALPHABETICALLY.value
        val value = mSharedPref?.getString(Constants.KEY_SORTING_CRITERIA, defaultCriteria)
            ?: defaultCriteria

        val defaultValue = Constants.SortingCriteria.VALUE_ALPHABETICALLY
        return when (value) {
            Constants.SortingCriteria.VALUE_ALPHABETICALLY.value -> Constants.SortingCriteria.VALUE_ALPHABETICALLY
            Constants.SortingCriteria.VALUE_COMPLETED.value -> Constants.SortingCriteria.VALUE_COMPLETED
            Constants.SortingCriteria.VALUE_DATE_ADDED.value -> Constants.SortingCriteria.VALUE_DATE_ADDED
            else -> defaultValue
        }

    }

    private fun getCriteria(): List<Criteria> {
        val criteria1 = Criteria(
            isSelected = getSelectedStatus(criteria = Constants.SortingCriteria.VALUE_ALPHABETICALLY),
            iconResource = R.drawable.ic_sort_alphabetically,
            title = getString(R.string.alphabetically)
        )
        val criteria2 = Criteria(
            isSelected = getSelectedStatus(criteria = Constants.SortingCriteria.VALUE_COMPLETED),
            iconResource = R.drawable.ic_circle_pressed,
            title = getString(R.string.completed)
        )
        val criteria3 = Criteria(
            isSelected = getSelectedStatus(criteria = Constants.SortingCriteria.VALUE_DATE_ADDED),
            iconResource = R.drawable.ic_circle_pressed,
            title = getString(R.string.date_added)
        )
        return listOf(criteria1, criteria2, criteria3)
    }

    private fun getSelectedStatus(criteria: Constants.SortingCriteria): Boolean {
        val sortingCriteria = getSortingCriteriaFromSharedPref()
        return sortingCriteria == criteria
    }

}