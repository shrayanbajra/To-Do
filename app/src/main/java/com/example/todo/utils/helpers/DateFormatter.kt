package com.example.todo.utils.helpers

import android.content.Context
import com.example.todo.R
import com.example.todo.framework.extensions.getYear
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    fun getFormattedDueDate(context: Context, date: Date): String {

        val isToday = date == Date()
        if (isToday) {

            return context.resources.getString(R.string.today)

        }

        val currentCalendar = Calendar.getInstance()
        val selectedDate = Calendar.getInstance().apply { timeInMillis = date.time }

        if (selectedDate.getYear() == currentCalendar.getYear()) {
            val simpleDateFormatter = SimpleDateFormat("d MMM", Locale.getDefault())
            return simpleDateFormatter.format(date)
        }

        val simpleDateFormatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        return simpleDateFormatter.format(date)

    }

}