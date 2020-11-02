package com.example.todo.di.navhost

import com.example.todo.di.navhost.mytasks.MyTasksFragmentModule
import com.example.todo.ui.addtask.AddTaskBottomSheet
import com.example.todo.ui.mytasks.MyTasksFragment
import com.example.todo.ui.viewtask.ViewTaskFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostFragmentBuildersModule {

    @ContributesAndroidInjector(modules = [MyTasksFragmentModule::class])
    abstract fun contributeMyTasksFragment(): MyTasksFragment

    @ContributesAndroidInjector
    abstract fun contributeViewTaskFragment(): ViewTaskFragment

    @ContributesAndroidInjector
    abstract fun contributeAddTaskBottomSheet(): AddTaskBottomSheet

}