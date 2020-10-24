package com.example.todo.di.app.modules

import androidx.lifecycle.ViewModelProvider
import com.example.todo.di.app.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}