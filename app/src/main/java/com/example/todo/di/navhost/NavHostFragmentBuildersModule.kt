package com.example.todo.di.navhost

import com.example.todo.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class NavHostFragmentBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            NavHostViewModelsModule::class
        ]
    )
    abstract fun contributeHomeFragment(): HomeFragment

}