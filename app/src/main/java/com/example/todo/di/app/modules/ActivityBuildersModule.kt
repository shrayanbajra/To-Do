package com.example.todo.di.app.modules

import com.example.todo.di.navhost.NavHostFragmentBuildersModule
import com.example.todo.di.navhost.NavHostViewModelsModule
import com.example.todo.ui.NavHostActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            NavHostFragmentBuildersModule::class,
            NavHostViewModelsModule::class
        ]
    )
    abstract fun contributeNavHostActivity(): NavHostActivity

}