package com.example.todo.ui.mytasks.sortbybottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Criteria
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortByBottomSheet(private val selectedListener: OnCriteriaSelectedListener) :
    BottomSheetDialogFragment() {

    private lateinit var mRvCriteria: RecyclerView
    private val mCriteriaAdapter = CriteriaAdapter(getOnCriteriaSelectedListener())

    private fun getOnCriteriaSelectedListener(): OnCriteriaSelectedListener {
        return object : OnCriteriaSelectedListener {

            override fun onSelected(title: String) {
                if (title == getString(R.string.alphabetically)) {
                    selectedListener.onSelected(title)
                    closeBottomSheet()
                }
            }

        }
    }

    private lateinit var mIvClose: ImageView

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
        mRvCriteria.layoutManager = LinearLayoutManager(context)
        mRvCriteria.adapter = mCriteriaAdapter
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

    private fun getCriteria(): List<Criteria> {
        val criteria1 = Criteria(
            iconResource = R.drawable.ic_sort_alphabetically, title = "Alphabetically"
        )
        val criteria2 = Criteria(iconResource = R.drawable.ic_circle_pressed, title = "Completed")
        return listOf(criteria1, criteria2)
    }

}