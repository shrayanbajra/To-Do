package com.example.todo.app

import android.app.Application

object AppUtils {

    private lateinit var APP: Application

    fun init(app: Application) {
        if (!AppUtils::APP.isInitialized) {
            APP = app
        }
    }

    fun getApp() = APP
}