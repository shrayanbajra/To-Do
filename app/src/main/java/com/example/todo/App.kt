package com.example.todo

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppUtils.init(this)
    }
}