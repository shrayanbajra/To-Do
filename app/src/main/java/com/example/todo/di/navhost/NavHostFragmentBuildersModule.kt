package com.example.todo.di.navhost

import com.example.todo.ui.home.HomeFragment
import com.example.todo.ui.viewtask.ViewTaskFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class NavHostFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeViewTaskFragment(): ViewTaskFragment

}