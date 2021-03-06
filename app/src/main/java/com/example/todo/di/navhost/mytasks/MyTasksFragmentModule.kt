package com.example.todo.di.navhost.mytasks

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todo.utils.Constants
import dagger.Module
import dagger.Provides

@Module
class MyTasksFragmentModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.PREF_MY_TASKS, Context.MODE_PRIVATE)
    }

}