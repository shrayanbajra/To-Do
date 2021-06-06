package com.example.todo.ui.my_tasks.sort_by_bottom_sheet

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
        holder.bind(currentCriteria)

    }

    override fun getItemCount() = criteria.size

    inner class CriteriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvCriteria: TextView = itemView.findViewById(R.id.tv_criteria)
        private val ivSelectedStatus: ImageView = itemView.findViewById(R.id.iv_selected_status)
        private val divider: View = itemView.findViewById(R.id.divider)

        fun bind(currentCriteria: Criteria) {

            ivIcon.setImageResource(currentCriteria.iconResource)
            tvCriteria.text = currentCriteria.title

            if (currentCriteria.isSelected) showTickMark()
            else hideTickMark()

            if (isLastItem(adapterPosition)) hideDivider()

            itemView.setOnClickListener { selectedListener.onSelected(currentCriteria.title) }

        }

        private fun showTickMark() {
            ivSelectedStatus.visibility = View.VISIBLE
        }

        private fun hideTickMark() {
            ivSelectedStatus.visibility = View.GONE
        }

        private fun isLastItem(position: Int) = position == itemCount - 1

        private fun hideDivider() {
            divider.visibility = View.INVISIBLE
        }

    }

    interface OnCriteriaSelectedListener {
        fun onSelected(title: String)
    }

}