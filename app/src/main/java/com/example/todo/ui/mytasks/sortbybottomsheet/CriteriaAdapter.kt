package com.example.todo.ui.mytasks.sortbybottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Criteria


class CriteriaAdapter(
    private val selectedListener: OnCriteriaSelectedListener
) :
    RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder>() {

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

        if (currentCriteria.isSelected)
            holder.ivSelectedStatus.visibility = View.VISIBLE
        else
            holder.ivSelectedStatus.visibility = View.GONE

        if (isLastItem(position))
            hideDivider(holder)

        holder.itemView.setOnClickListener {
            selectedListener.onSelected(currentCriteria.title)
        }
    }

    private fun isLastItem(position: Int) = position == itemCount - 1

    private fun hideDivider(holder: CriteriaViewHolder) {
        holder.divider.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return criteria.size
    }

    class CriteriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvCriteria: TextView = itemView.findViewById(R.id.tv_criteria)
        val ivSelectedStatus: ImageView = itemView.findViewById(R.id.iv_selected_status)
        val divider: View = itemView.findViewById(R.id.divider)

    }

}