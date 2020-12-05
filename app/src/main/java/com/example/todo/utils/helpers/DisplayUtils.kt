package com.example.todo.utils.helpers

import android.util.TypedValue
import android.view.View

object DisplayUtils {

    fun getDpValue(view: View, value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value,
            view.context.resources.displayMetrics
        )
    }

}