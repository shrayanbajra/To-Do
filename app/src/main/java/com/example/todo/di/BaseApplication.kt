package com.example.todo.di

import com.example.todo.app.AppUtils
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        AppUtils.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().application(this).build()
    }

}