package com.example.todo.di.navhost

import com.example.todo.di.navhost.mytasks.MyTasksFragmentModule
import com.example.todo.di.navhost.sortby.SortByBottomSheetModule
import com.example.todo.ui.add_task.AddTaskBottomSheet
import com.example.todo.ui.my_tasks.MyTasksFragment
import com.example.todo.ui.my_tasks.sort_by_bottom_sheet.SortByBottomSheet
import com.example.todo.ui.view_task.ViewTaskFragment
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

    @ContributesAndroidInjector(modules = [SortByBottomSheetModule::class])
    abstract fun contributeSortByBottomSheet(): SortByBottomSheet

}