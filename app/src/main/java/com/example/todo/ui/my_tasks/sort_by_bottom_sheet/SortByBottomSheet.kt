package com.example.todo.ui.my_tasks.sort_by_bottom_sheet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.data.Criteria
import com.example.todo.databinding.BottomSheetSortByBinding
import com.example.todo.utils.Constants
import com.example.todo.utils.SortingCriteria
import com.example.todo.utils.extensions.closeBottomSheet
import com.example.todo.utils.extensions.setupWithDefaultLinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SortByBottomSheet(
    private val selectedListener: CriteriaAdapter.OnCriteriaSelectedListener
) :
    BottomSheetDialogFragment() {

    private val mBinding get() = _binding!!
    private var _binding: BottomSheetSortByBinding? = null

    private val mCriteriaAdapter = CriteriaAdapter(getOnCriteriaSelectedListener())

    private fun getOnCriteriaSelectedListener(): CriteriaAdapter.OnCriteriaSelectedListener {
        return object : CriteriaAdapter.OnCriteriaSelectedListener {

            override fun onSelected(title: String) {
                selectedListener.onSelected(title)
                closeBottomSheet()
            }

        }
    }

    @Inject
    lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSortByBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

//    override fun getTheme(): Int {
//        return R.style.Custom_RoundedTop_BottomSheetDialog
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvCriteria(view.context)

    }

    private fun initRvCriteria(context: Context) {

        mBinding.rvCriteria.apply {
            setupWithDefaultLinearLayoutManager(context)
            adapter = mCriteriaAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.ivClose.setOnClickListener { closeBottomSheet() }

        val criteria = getCriteria()
        mCriteriaAdapter.setCriteria(criteria)

    }

    private fun getSortingCriteriaFromSharedPref(): SortingCriteria {

        val defaultCriteria = SortingCriteria.ALPHABETICALLY

        val value = mSharedPref.getString(Constants.KEY_SORTING_CRITERIA, defaultCriteria.value)
            ?: defaultCriteria.value
        return when (value) {
            SortingCriteria.ALPHABETICALLY.value -> SortingCriteria.ALPHABETICALLY
            SortingCriteria.COMPLETION_STATUS.value -> SortingCriteria.COMPLETION_STATUS
            SortingCriteria.DATE_ADDED.value -> SortingCriteria.DATE_ADDED
            else -> defaultCriteria
        }

    }

    private fun getCriteria(): List<Criteria> {
        val criteria1 = Criteria(
            isSelected = getSelectedStatus(criteria = SortingCriteria.ALPHABETICALLY),
            iconResource = R.drawable.ic_sort_alphabetically,
            title = getString(R.string.alphabetically)
        )
        val criteria2 = Criteria(
            isSelected = getSelectedStatus(criteria = SortingCriteria.COMPLETION_STATUS),
            iconResource = R.drawable.ic_circle_pressed,
            title = getString(R.string.completed)
        )
        val criteria3 = Criteria(
            isSelected = getSelectedStatus(criteria = SortingCriteria.DATE_ADDED),
            iconResource = R.drawable.ic_add_to_calendar,
            title = getString(R.string.date_added)
        )
        return listOf(criteria1, criteria2, criteria3)
    }

    private fun getSelectedStatus(criteria: SortingCriteria): Boolean {
        val sortingCriteria = getSortingCriteriaFromSharedPref()
        return sortingCriteria == criteria
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}