package com.example.todo.utils.helpers

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import com.example.todo.framework.extensions.getDayOfMonth
import com.example.todo.framework.extensions.getMonth
import com.example.todo.framework.extensions.getYear
import java.util.*

class DatePickerHelper {

    fun getDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener): Dialog {

        val today = Calendar.getInstance()
        val year = today.getYear()
        val month = today.getMonth()
        val dayOfMonth = today.getDayOfMonth()

        return DatePickerDialog(context, listener, year, month, dayOfMonth)
    }

    fun getDatePicker(
        context: Context,
        calendar: Calendar,
        listener: DatePickerDialog.OnDateSetListener,
    ): Dialog {

        val year = calendar.getYear()
        val month = calendar.getMonth()
        val dayOfMonth = calendar.getDayOfMonth()

        return DatePickerDialog(context, listener, year, month, dayOfMonth)
    }

    fun getDueDatePicker(
        context: Context,
        calendar: Calendar,
        listener: DatePickerDialog.OnDateSetListener,
    ): Dialog {

        val year = calendar.getYear()
        val month = calendar.getMonth()
        val dayOfMonth = calendar.getDayOfMonth()

        val datePickerDialog = DatePickerDialog(context, listener, year, month, dayOfMonth)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        return datePickerDialog

    }

}