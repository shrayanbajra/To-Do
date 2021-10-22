package com.example.todo.framework.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.framework.GridSpacingItemDecoration

private const val DEFAULT_SPAN_COUNT = 2

fun RecyclerView.setupWithDefaultLinearLayoutManager(context: Context): RecyclerView {
    return this.apply {
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}

fun RecyclerView.getDefaultGridLayoutManager(context: Context): GridLayoutManager {
    return GridLayoutManager(context, DEFAULT_SPAN_COUNT)
}

fun RecyclerView.getDefaultGridItemDecoration(context: Context): GridSpacingItemDecoration {
    val spacing = 16f.convertDpToPixel(context).toInt()
    val includeEdge = true
    return GridSpacingItemDecoration(DEFAULT_SPAN_COUNT, spacing, includeEdge)
}

fun RecyclerView.getDefaultLinearItemDecoration(context: Context): DividerItemDecoration {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val customDrawable = ContextCompat.getDrawable(
        context, R.drawable.bg_recycler_view_divider_item_decoration
    )
    if (customDrawable != null) itemDecoration.setDrawable(customDrawable)
    return itemDecoration
}