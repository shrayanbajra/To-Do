package com.example.todo.framework.extensions

import java.util.*

fun Calendar.getYear(): Int = this.get(Calendar.YEAR)

fun Calendar.setYear(year: Int): Unit = this.set(Calendar.YEAR, year)

fun Calendar.getMonth(): Int = this.get(Calendar.MONTH)

fun Calendar.setMonth(month: Int): Unit = this.set(Calendar.MONTH, month)

fun Calendar.getDayOfMonth(): Int = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.setDayOfMonth(dayOfMonth: Int): Unit = this.set(Calendar.DAY_OF_MONTH, dayOfMonth)

fun Calendar.getHourOfDay(): Int = this.get(Calendar.HOUR_OF_DAY)

fun Calendar.setHourOfDay(hourOfDay: Int): Unit = this.set(Calendar.HOUR_OF_DAY, hourOfDay)

fun Calendar.getMinute(): Int = this.get(Calendar.MINUTE)

fun Calendar.setMinute(minute: Int): Unit = this.set(Calendar.MINUTE, minute)

fun Calendar.getSecond(): Int = this.get(Calendar.SECOND)

fun Calendar.setSecond(second: Int): Unit = this.set(Calendar.SECOND, second)

fun Calendar.getMillisecond(): Int = this.get(Calendar.MILLISECOND)

fun Calendar.setMillisecond(millisecond: Int): Unit = this.set(Calendar.MILLISECOND, millisecond)