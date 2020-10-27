package com.example.todo.ui.mytasks

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Criteria


class CriteriaAdapter : RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder>() {

    private val criteria = arrayListOf<Criteria>()

    fun setCriteria(criteria: List<Criteria>) {
        this.criteria.clear()
        this.criteria.addAll(criteria)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CriteriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sort_by, parent, false)
        return CriteriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CriteriaViewHolder, position: Int) {
        val currentCriteria = criteria[position]

        holder.tvCriteria.text = currentCriteria.title
        holder.tvCriteria.setCompoundDrawablesWithIntrinsicBounds(
            currentCriteria.iconResource, 0, 0, 0
        )
        if (isLastItem(position)) {
            hideDivider(holder)
            updatePadding(holder)
        }
    }

    private fun isLastItem(position: Int) = position == itemCount - 1

    private fun hideDivider(holder: CriteriaViewHolder) {
        holder.divider.visibility = View.GONE
    }

    private fun updatePadding(holder: CriteriaViewHolder) {
        val eightDp = getDpValue(holder.itemView, 8f).toInt()
        val sixteenDp = getDpValue(holder.itemView, 16f).toInt()
        val thirtyTwoDp = getDpValue(holder.itemView, 32f).toInt()
        holder.tvCriteria.setPadding(sixteenDp, eightDp, sixteenDp, thirtyTwoDp)
    }

    private fun getDpValue(view: View, value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value,
            view.context.resources.displayMetrics
        )
    }

    override fun getItemCount(): Int {
        return criteria.size
    }

    class CriteriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvCriteria: TextView = itemView.findViewById(R.id.tv_criteria)
        val divider: View = itemView.findViewById(R.id.divider)

    }

}