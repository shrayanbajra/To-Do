package com.example.todo.ui

import com.example.todo.AppUtils
import com.example.todo.db.AppDatabase

abstract class BaseRepository {

    fun getDatabase(): AppDatabase {
        return AppDatabase.getDatabase(AppUtils.getApp())
    }
}