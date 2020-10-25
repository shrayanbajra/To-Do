package com.example.todo.db.task

import com.example.todo.utils.Constants

enum class TaskStatus(val value: Int) {
    NOT_DONE(value = Constants.STATUS_NOT_DONE),
    DONE(value = Constants.STATUS_DONE)
}