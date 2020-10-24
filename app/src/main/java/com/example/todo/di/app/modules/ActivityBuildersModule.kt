package com.example.todo.di.app.modules

import com.example.todo.ui.NavHostActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeNavHostActivity(): NavHostActivity

}